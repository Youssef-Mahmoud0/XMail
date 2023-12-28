<template>
  <div class="create-folder-container">
    <div class="create-folder-header">
      <span>New Label</span>
      <div @click="closeFolder" class="xButton">&times;</div>
    </div>
    <input v-model="folderName" class="input-box" placeholder="Enter folder name" />
    <div v-if="isEmpty" class="error-message">
      Please enter a valid name
    </div>
    <div v-if="isRepeated" class="error-message">
      This folder name already exists please choose another name
    </div>
    <div class="footer-tools">
      <div @click="closeFolder" class="cancel-button">Cancel</div>
      <div @click="createFolder" class="create-button">Create Folder</div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CreateFolder',
  props: ['labels','email'],
  data() {
    return {
      folderName: '',
      isEmpty: false,
      isRepeated: false,
    };
  },
  methods: {
   async sendNewFolder() {
    let newFolder = {
          source: this.folderName,
          email: this.email,
        };
        console.log(newFolder);
      await fetch('http://localhost:8080/createFolder', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(newFolder),
        }).then(response => {
          return response.json()
        }).then(data => {
          this.$emit('createFolder', data);
        })
          .catch((error) => {
            console.log(error + "error AAAAAAAAAAA");
          });
      this.folderName = '';
      this.closeFolder();
    },
    createFolder() {
    if (this.labels.some(label => (label.folderName===(this.folderName)))) {
        this.isRepeated = true;
        this.isEmpty = false;
      }
      else if (this.folderName === '') {
        this.isEmpty = true;
        this.isRepeated = false;
      }
      else {
       this.sendNewFolder();
      }
    },
    closeFolder() {
      this.$emit('createFolder-close-clicked', true);
    },
  },
  };
</script>
<style scoped>
.error-message {
  color: red;
  margin-top: 5px;
  font-size: 14px;
}

.create-folder-container {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 400px;
  padding: 20px;
  background-color: #fff;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  z-index: 1000;
  transition: all 0.3s ease;
}

.xButton {
  width: 30px;
  height: 30px;
  cursor: pointer;
  transition: color 0.3s ease;
  border-radius: 15px;
  font-size: 30px;
}

.cancel-button,
.create-button {
  width: 100px;
  padding: 10px;
  text-align: center;
  cursor: pointer;
  transition: background-color 0.3s ease, color 0.3s ease;
  margin-right: 10px;
}

.cancel-button:hover,
.xButton:hover {
  background-color: #f5f2f2;
}

.create-button {
  background-color: rgb(34, 62, 77);
  color: white;
}

.footer-tools {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}

.create-folder-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.create-folder-header span {
  font-size: 18px;
  font-weight: bold;
}

.create-folder-header button {
  background: none;
  border: none;
  font-size: 20px;
  cursor: pointer;
  color: #888;
  transition: color 0.3s ease;
}

.create-folder-header button:hover {
  color: #333;
}

.create-folder-container input {
  width: 100%;
  padding: 10px;
  margin-bottom: 15px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 14px;
  transition: border-color 0.3s ease;
}
</style>