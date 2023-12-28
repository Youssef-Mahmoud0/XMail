<template>
  <div :class="{ 'collapsed': isCollapsed }" id="sidebar">
    <div class="top-sidebar"> 
      <div @click="leaveWebsite"><img class="log-out-img" src="../assets/sidebar-icons/logout-icon.png" alt=""></div> 
      <div class="toggle-btn" @click="handleCollapse">☰</div> 
    </div>
    <div class="composing" :class="{ 'collapsed': isCollapsed }" @click="handleClick('compose')">
      <img class="compose-img" src="../assets/sidebar-icons/compose-icon.png">
      <div v-if="!isCollapsed">Compose</div>
    </div>
    <div class="menu-items" :class="{ 'collapsed': isCollapsed }">
      <div id="inbox" class="menu-item active" :class="{ 'collapsed': isCollapsed }" @click="handleClick('inbox')">
        <img class="compose-img" src="../assets/sidebar-icons/inbox-icon.png">
        <div v-if="!isCollapsed">Inbox</div>
      </div>
      <div id="sent" class="menu-item" :class="{ 'collapsed': isCollapsed }" @click="handleClick('sent')">
        <img class="compose-img" src="../assets/sidebar-icons/send-icon.png">
        <div v-if="!isCollapsed">Sent</div>
      </div>
      <div id="draft" class="menu-item" :class="{ 'collapsed': isCollapsed }" @click="handleClick('draft')">
        <img class="compose-img" src="../assets/sidebar-icons/drafts-icon.png">
        <div v-if="!isCollapsed">Drafts</div>
      </div>
      <div id="trash" class="menu-item" :class="{ 'collapsed': isCollapsed }" @click="handleClick('trash')">
        <img class="compose-img" src="../assets/sidebar-icons/trash-icon.png">
        <div v-if="!isCollapsed">Trash</div>
      </div>
      <div id="contacts" class="menu-item" @click="handleClick('contacts')">
        <img class="compose-img" src="../assets/sidebar-icons/contacts-icon.png">
        <div  v-if="!isCollapsed">contacts</div>
      </div>
      <div id="label" class="menu-item" :class="{ 'collapsed': isCollapsed }" @click="handleClick('label')">
        <div class="labels-plus">+</div>
        <div v-if="!isCollapsed">Labels</div>
      </div>
      <div @click="handleClick(label.folderName)" class="menu-item" v-for="label in labels" :key="label">
        <img class="compose-img" src="../assets/sidebar-icons/folders-icon.png">
        <div  v-if="!isCollapsed">{{ label.folderName }}</div>
        <div id="foldersTools">
          <div class="trash-icon" v-if="!isCollapsed" @click="displayifToRemove(label.folderName)">
            <div><img class="rename-icon" src="../assets/sidebar-icons/trash3-icon.png"></div>
          </div>
          <div v-if="!isCollapsed"><img class="rename-icon" src="../assets/sidebar-icons/rename-icon.png"
              @click="displayifToRename(label.folderName)"></div>
        </div>
      </div>
    </div>
    <div v-if="renameFolder" class="delete-rename-folder-container">
      <div class="delete-rename-folder-header">
        <div @click="closeRename" class="xButton">&times;</div>
      </div>
      <div>
        <input type="text" v-model="folderName" id="folderNameInput">
      </div>
      <div>
        <div v-if="isEmpty" class="error-message">
          Please enter a valid name
        </div>
        <div v-if="isRepeated" class="error-message">
          This folder name already exists please choose another name
        </div>
        <div class="footer-tools">
          <div @click="closeRename" class="cancel-button">Cancel</div>
          <div @click="renamingFolder" class="delete-rename-button">Rename</div>
        </div>
      </div>
    </div>
    <div v-if="deleteFolder" class="delete-rename-folder-container">
      <div class="delete-rename-folder-header">
        <div @click="deleteFolder = !deleteFolder" class="xButton">&times;</div>
      </div>
      <div>
        Are you sure you want to delete it ??<span>(All its content would be deleted permanently) </span>
      </div>
      <div>
        <div class="footer-tools">
          <div @click="deleteFolder = !deleteFolder" class="cancel-button">Cancel</div>
          <div @click="deletingFolder" class="delete-rename-button">Delete</div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'sidebar',
  props: ['labels'],
  data() {
    return {
      isCollapsed: false,
      deleteFolder: false,
      folderName: '',
      renameFolder: false,
      isEmpty: false,
      isRepeated: false,
      oldfolderName: '',
    };
  },
  methods: {
    displayifToRename(folderName) {
      this.deleteFolder = false;
      this.renameFolder = !this.renameFolder;
      this.oldfolderName = folderName;
      this.folderName = folderName;
      this.isEmpty = false;
      this.isRepeated = false;
    },
    displayifToRemove(folderName) {
      this.renameFolder = false;
      this.deleteFolder = !this.deleteFolder;
      this.folderName = folderName;
    },
    deletingFolder() {
      console.log(this.folderName);
      this.deleteFolder = !this.deleteFolder;
      this.$emit('deleteFolder', this.folderName);
    },
    renamingFolder() {
      if (this.labels.some(label => (label.folderName === (this.folderName) && (label.folderName !== this.oldfolderName)))) {
        this.isRepeated = true;
        this.isEmpty = false;
      }
      else if (this.folderName === '') {
        this.isEmpty = true;
        this.isRepeated = false;
      }
      else {
        this.closeRename();
        this.$emit('renameFolder', this.oldfolderName, this.folderName);
      }
    },
    handleCollapse() {
      this.isCollapsed = !this.isCollapsed;
      this.$emit('collpase-button-clicked', this.isCollapsed);
    },
    handleClick(pageToLoad) {
      // this.isCollapsed=true; 
      this.$emit('button-clicked', pageToLoad);
      if (pageToLoad === 'inbox') {
        document.querySelector("#inbox").classList.add("active")
        document.querySelector("#sent").classList.remove("active")
        document.querySelector("#contacts").classList.remove("active")
        document.querySelector("#draft").classList.remove("active")
        document.querySelector("#trash").classList.remove("active")
      }
      else if (pageToLoad === 'sent') {
        document.querySelector("#sent").classList.add("active")
        document.querySelector("#contacts").classList.remove("active")
        document.querySelector("#inbox").classList.remove("active")
        document.querySelector("#draft").classList.remove("active")
        document.querySelector("#trash").classList.remove("active")
      }
      else if (pageToLoad === 'draft') {
        document.querySelector("#inbox").classList.remove("active")
        document.querySelector("#sent").classList.remove("active")
        document.querySelector("#contacts").classList.remove("active")
        document.querySelector("#draft").classList.add("active")
        document.querySelector("#trash").classList.remove("active")
      }
      else if (pageToLoad === 'contacts') {
        document.querySelector("#contacts").classList.add("active")
        document.querySelector("#sent").classList.remove("active")
        document.querySelector("#inbox").classList.remove("active")
        document.querySelector("#draft").classList.remove("active")
        document.querySelector("#trash").classList.remove("active")
      }
      else if (pageToLoad === 'trash') {
        document.querySelector("#contacts").classList.remove("active")
        document.querySelector("#sent").classList.remove("active")
        document.querySelector("#inbox").classList.remove("active")
        document.querySelector("#draft").classList.remove("active")
        document.querySelector("#trash").classList.add("active")
      }
    },
    closeRename() {
      this.renameFolder = !this.renameFolder;
      this.isEmpty = false;
      this.isRepeated = false;
    },
    leaveWebsite(){ 
      this.$emit('leaving') 
    },
  },
};
</script>
<style scoped>
body {
  margin: 0;
  font-family: 'Arial', sans-serif;
  display: flex;
}

.active {
  background-color: rgb(34, 62, 77);
}

#sidebar {
  width: 240px;
  height: 100vh;
  background-color: #29495A;
  padding: 20px;
  position: relative;
  /* top: 8px; */
  transition: width 0.3s;
  overflow-x: hidden;
  box-shadow: 2px 0px 5px rgb(34, 62, 77);
}

.menu-items {
  padding-top: 10px;
}

.menu-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  cursor: pointer;
  padding: 10px;
  color: #fff;
  text-decoration: none;
  transition: background-color 0.3s;
}

#foldersTools {
  display: flex;
  align-items: center;
}

.rename-icon {
  margin-left: 10px;
}

img {
  width: 25px;
  margin-right: 8px;
}

.composing {
  display: flex;
  align-items: center;
  align-content: space-between;
  padding: 15px;
  color: #fff;
  font-size: 16px;
  height: 20px;
  cursor: pointer;
  transition: background-color 0.3s;
  margin-top: 25px;
  background-color: rgb(32, 58, 74);
  width: 120px;
  margin-left: 30px;
}

.composing.collapsed {
  width: 20px;
  background-color: transparent;
  margin: 0px;
}

.menu-item:hover {
  background-color: rgb(34, 62, 77);
}

#sidebar.collapsed {
  width: 100px;
}

.menu-items.collapsed {
  padding-top: 0;
}

.toggle-btn {
  cursor: pointer;
  font-size: 20px;
  color: #fff;
  background: none;
  border: none;
  position: absolute;
  top: 15px;
  right: 10px;
}

.labels-plus {
  font-size: 40px;
  margin-right: 10px;
}

.custom-folders {
  display: flex;
  align-content: space-between
}

span,
.error-message {
  color: red;
  margin-top: 5px;
  font-size: 16px;
  font-weight: bold;
  display: block;
}

.delete-rename-folder-container {
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
.delete-rename-button {
  width: 100px;
  padding: 10px;
  text-align: center;
  background-color: rgb(34, 62, 77);
  color: white;
  cursor: pointer;
  border: none;
  border-radius: 12px;
  transition: background-color 0.3s ease, color 0.3s ease;
  margin-right: 10px;
}

.xButton:hover {
  background-color: #f5f2f2;
}

.cancel-button,
.delete-rename-button {
  width: 100px;
  padding: 10px;
  text-align: center;
  cursor: pointer;
  transition: background-color 0.3s ease, color 0.3s ease;
  margin-right: 10px;
}


.delete-rename-folder-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.delete-rename-folder-header button {
  background: none;
  border: none;
  font-size: 20px;
  cursor: pointer;
  color: #888;
  transition: color 0.3s ease;
}

.footer-tools {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}

.delete-rename-folder-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.rename-icon {
  width: 17px;
}

#folderNameInput {
  width: 100%;
  padding: 8px;
  margin-top: 5px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}
.log-out-img{ 
  width: 20px; 
  cursor: pointer; 
} 
 
.top-sidebar{ 
  display: flex; 
  justify-content: space-between; 
  align-items: center; 
}
</style>
  