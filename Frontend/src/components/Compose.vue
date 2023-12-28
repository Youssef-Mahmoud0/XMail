<template>
  <div class="compose-container">
    <div v-if="loadDraftMessage"></div>
    <div class="compose-box" :class="{ 'minimized': isMinimized }">
      <div class="compose-header">
        <div class="header-buttons">
          <button class="minimize-button" @click="minimizeCompose">&minus;</button>
          <button class="close-button" @click="closeCompose">&times;</button>
        </div>
        <div v-if="!isMinimized" class="header-inputs">
          <div class="to-bar">
            <input v-model="tempToStr" placeholder="To (comma separated) " class="compose-input" />
            <div :class="{ 'error-message': !isToCorrect && this.pageToLoad !=='trash' }">**</div>
          </div>
          <input v-model="subject" placeholder="Subject" class="compose-input" />
        </div>
      </div>
      <div v-if="!isMinimized" class="message-body">
        <div class="compose-body">
          <textarea v-model="content" placeholder="Compose your message..." class="compose-textarea"></textarea>
          <input id="fileInput" type="file" @change="handleFileUpload" multiple class="compose-file-input"
            style="display: none" />
          <div v-if="attachments.length" class="attachment-info">
            <div v-for="(attachment, index) in attachments" :key="index" class="attachment-info-item">
              <a :href="decodeBase64(attachment.content)" :download="attachment.name" target="_blank">{{
                attachment.name }}</a>
              <div class="attachment-size">({{ formatSize(attachment.size) }})</div>
              <button @click="removeAttachment(index)" class="delete-button">X</button>
            </div>
          </div>
        </div>
      </div>
      <div v-if="!isMinimized" class="tools-bar">
        <label for="fileInput" class="file-input-label">
          <span class="icon">&#128206;</span>
        </label>
        <div @click="saveDraft" class="save-send-buttons">Draft</div>
        <div v-if="isSaved" class="savedSentMessage"> Saved to draft Successfully</div>
        <div @click="sendMessage" class="save-send-buttons" :class="{ 'inactive': !(this.pageToLoad !== 'trash'||!this.isDraft) }" >Send</div>
        <div v-if="isSent" class="savedSentMessage">Message sent Successfully</div>
        <div class="priority-dropdown">
          <div :class="{ 'error-message': !prioritySet && this.pageToLoad !=='trash' }">**&nbsp;</div>
          <label for="prioritySelect">Priority:</label>
          <select id="prioritySelect" v-model="priority">
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
          </select>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'compose',
  computed: {
    loadDraftMessage() {
      if (this.isDraft&& this.draftMessage!==null) {
        this.tempToStr = this.draftMessage.to.join(',');
        this.content = this.draftMessage.content;
        this.subject = this.draftMessage.subject;
        this.attachments = this.draftMessage.attachments;
        this.priority = this.draftMessage.priority;
        this.mailType = 'draft'
      }
    }
  },
  props: ['email', 'draftMessage', 'isDraft', 'pageToLoad'],
  data() {
    return {
      isMinimized: false,
      to: [],
      subject: 'no subject',
      content: '',
      attachments: [],
      priority: 0,
      mailType: 'sent',
      date: '',
      time: '',
      tempToStr: '',
      isSaved: false,
      isSent: false,
      prioritySet: true,
      isToCorrect: true,
    };
  },
  methods: {
    saveDraft() {
      this.sendDraft();
      setTimeout(() => {
        this.closeCompose();
      }, 1000);
    },
    minimizeCompose() {
      this.isMinimized = !this.isMinimized;
    },
    closeCompose() {
      this.$emit('compose-close-clicked', true);
    },
    async sendMessage() { 
        if (this.validateMessageData() && (this.pageToLoad !== 'trash'||!this.isDraft)) { 
          let objectFromSend = await this.fetchSpecificArray(this.mailType, 'addMail'); 
          this.isSent = true;
          this.updateSentAndDraft(objectFromSend.sourceMails, objectFromSend.destinationMails) 
          setTimeout(() => {
            this.closeCompose();
          }, 1000);
        } 
      }, 
      async fetchSpecificArray(type, path){ 
        let system ={}
        let messageInfo = this.intializeMessage(type) 
        console.log(messageInfo);
        await fetch('http://localhost:8080/' + path, { 
            method: 'POST', 
            headers: { 
                'Content-Type': 'application/json', 
            }, 
            body: JSON.stringify(messageInfo), 
        }).then(response => { 
            return response.json() 
        }).then(data => { 
            system = data 
        }).catch((error) => { 
          console.log(error + "error AAAAAAAAAAA"); 
        }); 
        return system 
      }, 
      async sendDraft(){ 
        this.to.push(this.tempToStr)
        let draftArr = await this.fetchSpecificArray('draft', 'draftMail'); 
        this.isSaved = true
        this.returnDraftArray(draftArr.mail) 
      }, 
      updateSentAndDraft(sentArr, draftArr) { 
        this.$emit('pressSend', sentArr, draftArr) 
      }, 
      intializeMessage(type){ 
        let tempMailID = -1;
        if(this.draftMessage !== null && this.isDraft)
          tempMailID = this.draftMessage.mailID
        let messageInfo = { 
          from: this.email, 
          to: this.to, 
          subject: this.subject, 
          content: this.content, 
          priority: this.priority, 
          mailID: tempMailID, 
          attachments: this.attachments, 
          date: '', 
          time: '', 
          mailType: type 
        } 
        return messageInfo 
      }, 
      returnDraftArray(draftArr) { 
        this.$emit('pressDraft', draftArr) 
      },
    validateMessageData() {
      this.prioritySet = this.priority !== 0;
      this.isToCorrect = this.validateToData();
      return this.prioritySet && this.isToCorrect;
    },
    validateToData() {
      this.to = this.tempToStr.split(',').map(email => email.trim());
      return this.to.every(to => /^[^\s@]+@Xmail\.com$/.test(to)) && this.to.length > 0;
    },
    handleFileUpload(event) {
      let files = event.target.files;

      for (let i = 0; i < files.length; i++) {
        const file = files[i];
        const reader = new FileReader();
        reader.onload = (e) => {
          const attachment = {
            name: file.name,
            size: file.size,
            content: e.target.result.split(',')[1],
          };
          this.attachments.push(attachment);
        };
        reader.readAsDataURL(file);
      }
    },
    decodeBase64(base64String) {
      const byteCharacters = atob(base64String);
      const byteNumbers = new Array(byteCharacters.length);

      for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
      }
      const byteArray = new Uint8Array(byteNumbers);
      const blob = new Blob([byteArray], { type: 'application/octet-stream' });
      return URL.createObjectURL(blob);
    },
    formatSize(sizeInBytes) {
      const kilobytes = sizeInBytes / 1024;
      if (kilobytes < 1) {
        return sizeInBytes + ' B';
      } else {
        const megabytes = kilobytes / 1024;
        if (megabytes < 1) {
          return kilobytes.toFixed(2) + ' KB';
        } else {
          return megabytes.toFixed(2) + ' MB';
        }
      }
    },
    removeAttachment(index) {
      this.attachments.splice(index, 1);
    },
  },
};
</script>

<style scoped>
.error-message {
  color: red;
  font-size: 25px;
}

.save-send-buttons {
  background-color: rgb(34, 62, 77);
  color: white;
  padding: 10px 20px;
  cursor: pointer;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  transition: background-color 0.3s ease, color 0.3s ease;

}

.savedSentMessage {
  font-size: 20px;
  position: fixed;
  top: 30%;
  left: 20%;
  width: 400px;
  padding: 20px;
  background: white;
  margin: 20px;
  border-radius: 10px;
  box-shadow: 0px 0px 20px 6px rgba(0, 0, 0, 0.1);
}

.save-send-buttons:hover {
  background-color: rgb(34, 62, 77);
}

.header-buttons {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  height: 20px;
  margin: 0px;
}

.compose-box.minimized {
  height: 40px;
}

.header-inputs {
  display: flex;
  flex-direction: column;
}

.minimize-button,
.close-button {
  background: none;
  border: none;
  font-size: 18px;
  cursor: pointer;
  margin-right: 10px;
  color: #777;
  display: inline-block;
}

.minimize-button:hover,
.close-button:hover {
  color: #333;
}

.compose-container {
  position: fixed;
  bottom: 0px;
  right: 25px;
  width: 500px;
  z-index:1000;
}

.compose-box {
  background-color: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  height: 560px;
}

.compose-header {
  padding: 10px;
}

.compose-input,
.compose-textarea,
.compose-file-input,
.compose-button {
  margin: 5px;
  padding: 8px;
  font-size: 14px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  width: 100%
}

.compose-input {
  font-size: 14px;
  border: none;
  border-bottom: 1px solid #d5d9dc;
  padding: 5px;
}

.compose-body {
  padding: 10px;
}

.compose-textarea {
  resize: none;
  font-size: 16px;
  line-height: 1.5;
  min-height: 220px;
  border: none;
  padding: 8px;
  box-sizing: border-box;
  width: 100%;
}

.compose-file-input {
  cursor: pointer;
  border: none;
}

.file-input-label {
  display: inline-block;
  cursor: pointer;
  background-color: #f5f5f5;
  padding: 8px;
  transition: background 0.3s;
}

.file-input-label:hover {
  background-color: #e0e0e0;
}

.icon {
  font-size: 16px;
  margin-right: 5px;
}

.attachment-info {
  display: flex;
  flex-direction: column;
  border: none;
  margin-top: 10px;
  max-height: 100px;
  overflow: auto;
  padding: 8px;
}

.attachment-info-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 5px;
  margin-bottom: 5px;
  background-color: #f5f5f5;
}

.attachment-name {
  color: rgb(34, 62, 77);
  font-size: 18px;
}

.attachment-size {
  color: #777;
}

#prioritySelect {
  padding: 5px;
  font-size: 14px;
  border: 1px solid #ccc;
  box-sizing: border-box;
}

.tools-bar {
  position: fixed;
  bottom: 0px;
  display: flex;
  flex: auto;
  justify-content: space-between;
  align-items: center;
  border: solid 1px #ccc;
  height: 40px;
  width: 500px;
}

.delete-button {
  margin-left: 10px;
  border: none;
  padding: 5px 10px;
  cursor: pointer;
  background-color: transparent;
  display: fixed;
  right: 0;
}

.priority-dropdown,
.to-bar {
  display: flex;
  align-items: center;
}

.inactive{
  background-color: #777;
}
.inactive:hover{
  background-color: #777;
}


</style>
