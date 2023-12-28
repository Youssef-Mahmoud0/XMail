<template>
  <div class="home-container">
    <sidebar :labels="customFolders" @button-clicked="handleButtonClicked" @collpase-button-clicked="handleCollapse"
      @deleteFolder="deleteFolder" @renameFolder="renameFolder" @leaving="logout = true" />
    <div class="Content">
      <div class="header-tools">
        <div id="searching">
          <img src="../assets/search.png" class="searchIcon" @click="search">
          <input type="text" v-model="searchText" class="search-icon">
          <img v-if="pageToLoad != 'contacts'" src="../assets/filter.png" @click="showSearchBox = !showSearchBox"
            class="filterIcon">
        </div>
        <div class="search-box" v-if="showSearchBox">
          <span @click="showSearchBox = !showSearchBox">x</span>
          <div>
            <label for="FromCri">From</label>
            <input type="text" id="FromCri" class="inputBox" v-model="fromCri">
          </div>
          <div>
            <label for="ToCri">To</label>
            <input type="text" id="ToCri" class="inputBox" v-model="toCri">
          </div>
          <div>
            <label for="SubjectCri">Subject</label>
            <input type="text" id="SubjectCri" class="inputBox" v-model="subjectCri">
          </div>
          <div>
            <label for="contentCri">Content</label>
            <input type="text" id="contentCri" class="inputBox" v-model="contentCri">
          </div>
          <div>
            <label for="DateCri">Date</label>
            <input type="date" id="DateCri" class="inputBox" v-model="dateCri">
          </div>
          <div>
            <label for="YearCri">Year</label>
            <input type="text" id="YearCri" class="inputBox" v-model="yearCri">
          </div>
          <div>
            <label for="attachmentNameCri">AttachmentName</label>
            <input type="text" id="attachmentNameCri" class="inputBox" v-model="attachmentNameCri">
          </div>
          <div>
            <label for="PriorityCri">Priority</label>
            <select id="prioritySelect" class="inputBox" v-model="priorityCri">
              <option value="1">1</option>
              <option value="2">2</option>
              <option value="3">3</option>
              <option value="4">4</option>
            </select>
          </div>
          <button class="filterButton" @click="filter">Filter</button>
        </div>
      </div>
      <createfolder :labels="customFolders" :email="this.email" v-if="labelBox" @createFolder="Createfolder"
        @createFolder-close-clicked="closecreateFolder" />
      <compose :email="email" :draftMessage="draftToEdit" :isDraft="isDraft" :pageToLoad="pageToLoad"
        @pressDraft="refreshDraftArr" @pressSend="refreshAfterSend" v-if="composeBox"
        @compose-close-clicked="closeCompose" />
      <mail v-if="this.currentMail != null && currentMail.mailType != 'draft'" @back-clicked="currentMail = null"
        :key="currentMail" :from="currentMail.from" :to="currentMail.to" :content="currentMail.content"
        :attachments="currentMail.attachments" :subject="currentMail.subject" :localDate="currentMail.localDate"
        :localTime="currentMail.localTime" />
      <div v-if="contactPage">
        <button class="newContactButton" @click="addContact = !addContact">add new contact</button>
        <select v-if="pageToLoad === 'contacts'" id="mySelect" @change="getContactsSorted" v-model="sortType">
          <option value="default" selected>Default</option>
          <option value="alphabitical">Alphabitical</option>
        </select>
        <div v-if="addContact" class="create-contact-container">
          <div class="create-contact-header">
            <strong>New Contact</strong>
            <div @click="closeCreate" class="xButton">&times;</div>
          </div>
          <input v-model="contactName" class="input-box" placeholder="Enter contact name" />
          <input v-model="tempToStr" placeholder="emails (comma separated) " class="compose-input" />
          <div class="error-message">
            <span v-if="isEmpty">Please enter a valid name</span>
            <span v-else-if="isRepeated">This contact name already exists please choose another name</span>
            <span v-else-if="isEmailIncorrect">Please enter a valid email</span>
          </div>
          <div class="footer-tools">
            <div @click="closeCreate" class="cancel-button">Cancel</div>
            <div @click="createContact" class="create-button">Create Contact</div>
          </div>
        </div>
        <div class="scrollContacts">
          <div v-for="(contact, index) in showableContacts" :key="index" class="contactContainer">
            <div class="personalInfo">
              <div>
                <strong>Name:</strong>
                {{ contact.name }}
              </div>
              <div class="email">
                <strong>Email:</strong>
                <ul>
                  <li v-for="(email, index) in contact.emails" :key="index">{{ email }}</li>
                </ul>
              </div>
            </div>
            <div class="contactTools">
              <div @click="toggleTools(index)" class="three-dots">&#8230;</div>
              <ul v-if="showTools[index]">
                <li @click="editContact(contact, index)">Edit</li>
                <li @click="contactTodelete = contact, deletecontact = !deletecontact">Delete</li>
              </ul>
            </div>
            <div v-if="deletecontact" class="doOPeration-container">
              <div class="doOPeration-header">
                <div @click="deletecontact = !deletecontact" class="xButton">&times;</div>
              </div>
              <div>
                <p style="text-align: center; font-size:18px;">Are you sure you want to delete it ??</p>
                <span>(All its content would be deleted permanently) </span>
              </div>
              <div>
                <div class="footer-tools">
                  <div @click="deletecontact = !deletecontact" class="cancel-button">Cancel</div>
                  <div @click="deleteContact(this.contactTodelete)" class="doOPeration-button">Delete</div>
                </div>
              </div>
            </div>
            <div v-if="editingContact" class="create-contact-container">
              <div class="create-contact-header">
                <strong>Edit Contact</strong>
                <div @click="closeCreate" class="xButton">&times;</div>
              </div>
              <input v-model="contactName" class="input-box" placeholder="Enter new contact name" />
              <input v-model="tempToStr" placeholder="emails (comma separated) " class="compose-input" />
              <div class="error-message">
                <span v-if="isEmpty">Please enter a valid name</span>
                <span v-else-if="isRepeated">This contact name already exists please choose another name</span>
                <span v-else-if="isEmailIncorrect">Please enter a valid email</span>
              </div>
              <div class="footer-tools">
                <div @click="closeCreate" class="cancel-button">Cancel</div>
                <div @click="saveContactChanges" class="create-button">Save Changes</div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- <input type="checkbox" id="sellectAll" @click="this.sellectAll = !this.sellectAll"> -->
      <div class="navigate-pages"
        v-if="(currentMail === null || currentMail.mailType === 'draft') && !contactPage && messages.length > 0">
        <div id="MailTools">
          <div class="sellect-all-box">
            <input type="checkbox" v-model="selectAll" @change="handleSelectAll()" id="myCheckbox" name="myCheckbox">
          </div>
          <div v-if="pageToLoad !== 'draft' && pageToLoad !== 'trash' && pageToLoad !== 'contact'" class="move-box">
            <div @click="moveSelectBox = !moveSelectBox" class="move-button">Move</div>
            <div v-if="moveSelectBox" class="select-container">
              <select id="selectBox" class="custom-select" size="5">
                <option v-for="folder in customFolders" :key="folder.folderName" :value="folder.folderName"
                  @click="moveToCustomFolder(folder.folderName)">
                  {{ folder.folderName }}
                </option>
              </select>
            </div>
          </div>
          <div class="delete-sellected" v-if="this.pageToLoad === 'trash'"
            @click="restoreTrash(this.selectedMessages), (this.selectAll = false)">
            <img class="rename-icon" src="../assets/sidebar-icons/restore-icon.png">
          </div>
          <div class="delete-sellected" @click="moveMessagesToTrash(this.selectedMessages), this.selectAll = false">
            <img class="rename-icon" src="../assets/sidebar-icons/trash2-icon.png">
          </div>
          <select v-if="pageToLoad != 'trash' && pageToLoad != 'draft' && pageToLoad != 'contacts'" id="mySelect"
            v-model="sortType" @change="getMessagesSorted">
            <option value="default" selected>Default</option>
            <option value="priority">Priority</option>
          </select>
        </div>
        <div>{{ (currentPage - 1) * 10 + 1 }}-{{ pageEnd }} of {{ messages.length }}
          <button @click="prevPage" class="navigate-pages-button" :class="{ 'disabled-navigation': startOfPages }">
            &lt;
          </button>
          <button @click="nextPage" class="navigate-pages-button" :class="{ 'disabled-navigation': endOfPages }">
            &gt;
          </button>
        </div>
      </div>
      <!-- <div v-if=" messages.length ===0 && showableMessages.length ===0 && pageToLoad!=='contacts'"> No messages in {{ pageToLoad }} to display</div> -->
      <div v-if="inboxVisible &&
        (currentMail === null || currentMail.mailType === 'draft') &&
        !contactPage
        " class="inbox-container">
        <div v-for="(msg, index) in paginatedMessages" :key="index" class="message-item">
          <div class="priority">{{ msg.priority }}</div>
          <div class="select-box">
            <input type="checkbox" @change="handleCheckboxChange(msg)" v-model="msg.selected" class="message-checkbox" />
          </div>
          <div class="message-outside" @click="checkCurrentMail(msg)">
            <div class="message-header">
              <div class="containsMessage">
                <div class="message-from" v-if="msg.mailType == 'inbox' || msg.mailType == 'draft'">{{ msg.from }}</div>
                <div class="message-from" v-else>{{ msg.to[0] }}</div>
                <div class="message-subject">{{ msg.subject }}</div>
              </div>
              <div class="message-date">{{ msg.localDate }}</div>
            </div>
          </div>
          <div class="message-tools">
            <div v-if="this.pageToLoad === 'trash'" @click="restoreTrash([msg])">
              <img class="restore" src="../assets/sidebar-icons/restore-icon.png">
            </div>
            <div @click="moveMessagesToTrash([msg])"><img class="trash" src="../assets/sidebar-icons/trash2-icon.png">
            </div>
          </div>
        </div>
        <div v-if="this.messages.length == 0 && searchOrfilter == true">No messages matched your search. </div>
      </div>
    </div>
    <div v-if="logout" class="doOPeration-container">
      <div class="doOPeration-header">
        <div @click="logout = !logout" class="xButton">&times;</div>
      </div>
      <div>
        <span>Are you sure you want to log out?? </span>
      </div>
      <div>
        <div class="footer-tools">
          <div @click="logout = !logout" class="cancel-button">Cancel</div>
          <div @click="logOut" class="doOPeration-button">Log out</div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import sidebar from "@/components/sideBar.vue";
import compose from "@/components/Compose.vue";
import mail from "@/components/Mail.vue";
import createfolder from "@/components/createFolder.vue";
export default {
  name: "home",
  mounted() {
    this.getCookie();
    this.loadSystem();
  },
  data() {
    return {
      logout: false,
      deletecontact: false,
      contactTodelete: '',
      moveSelectBox: false,
      sortType: 'default',
      showableContacts: [],
      isEmailIncorrect: false,
      oldContactName: '',
      editingContact: false,
      showTools: [],
      showEmails: false,
      tempToStr: '',
      contactName: '',
      contactEmails: [],
      isEmpty: false,
      isRepeated: false,
      addContact: false,
      contactPage: false,
      contacts: [],
      contactIndex: 0,
      currentMail: null,
      attachmentNameCri: '',
      fromCri: '',
      toCri: '',
      subjectCri: '',
      contentCri: '',
      dateCri: '',
      priorityCri: '',
      yearCri: '',
      searchText: '',
      labelBox: false,
      globalMailNumber: 0,
      email: "",
      selectedMessages: [],
      currentPage: 1,
      messagesPerPage: 10,
      totalPages: 1,
      showSearchBox: false,
      pageEnd: 0,
      startOfPages: true,
      endOfPages: true,
      draftToEdit: null,
      composeBox: false,
      inboxVisible: true,
      collapsed: false,
      selectAll: false,
      sent: [],
      inbox: [],
      drafts: [],
      customFolders: [],
      messages: [],
      trash: [],
      pageToLoad: "inbox",
      isDraft: false,
      searchOrfilter: false,
      showableMessages: [],
    };
  },
  components: { sidebar, compose, mail, createfolder },
  computed: {
    paginatedMessages() {
      if (this.messages !== null) {
        this.totalPages = Math.ceil(this.messages.length / 10);
        if (this.totalPages == 1 || this.currentPage == this.totalPages)
          this.endOfPages = true;
        else this.endOfPages = false;
        if (this.totalPages == 1 || this.currentPage == 1)
          this.startOfPages = true;
        else this.startOfPages = false;
        if (this.currentPage == (this.totalPages + 1) && this.currentPage !== 1) {
          this.currentPage--;
        }
        const start = (this.currentPage - 1) * this.messagesPerPage;
        let end = start + this.messagesPerPage;
        const messagesEnd = this.messages.length;
        if (end > messagesEnd) {
          end = messagesEnd;
        }
        this.pageEnd = end;
        this.showableMessages = this.messages.slice(start, end);
        return this.showableMessages;
      }
    },
  },
  methods: {
    setDataToSystem(data) {
      this.customFolders = data.customFolders;
      this.globalMailNumber = data.globalMailNumber;
      this.drafts = data.draftFolder.mail;
      this.inbox = data.inboxFolder.mail;
      this.sent = data.sentFolder.mail;
      this.trash = data.trashFolder.mail;
      this.contacts = data.contacts;
      if (this.contacts !== null && this.contacts.length > 0)
        this.showTools = new Array(this.contacts.length).fill(false)
    },
    async moveToCustomFolder(folderName) {
      if (this.selectedMessages.length > 0) {
        let system = {
          email: this.email,
          sourceMails: this.selectedMessages,
          destinationMails: [],
          source: this.pageToLoad,
          destination: folderName,
        };
        system = await this.move("moveToFolder", system);

        if (this.pageToLoad === "inbox") this.inbox = system.sourceMails;
        else if (this.pageToLoad === "sent") this.sent = system.sourceMails;

        this.customFolders.forEach((folder) => {
          if (this.pageToLoad === folder.folderName) {
            folder.mail = system.sourceMails;
          }
          if (folderName === folder.folderName)
            folder.mail = system.destinationMails;
        });
        if (system.sourceMails !== null)
          this.messages = system.sourceMails;
        else
          this.messages = []

        this.selectAll = false;
        this.handleSelectAll();
      }
    },
    Createfolder(data) {
      this.customFolders = data;
      console.log(this.customFolders);
    },
    async deleteContact(contact) {
      this.deletecontact = false;
      let contactToDelete = {
        contact,
        email: this.email,
        source: contact.name,
      };
      await fetch('http://localhost:8080/deleteContact', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(contactToDelete),
      }).then(response => {
        return response.json()
      }).then(data => {
        console.log(data);
        this.contacts = data;
        this.showableContacts = data;
        this.sortType = 'default';
        this.showTools.pop()
        if (this.showTools.length > 0)
          this.showTools.fill(false)
      })
        .catch((error) => {
          console.log(error + "error AAAAAAAAAAA");
        });
    },
    checkContactValues() {
      if (this.contactName === '') {
        this.isEmpty = true;
        this.isRepeated = false;
        this.isEmailIncorrect = false;
        return false;
      }
      else if (this.contacts.some(contact => (contact.name === (this.contactName) && (contact.name !== this.oldContactName)))) {
        this.isRepeated = true;
        this.isEmpty = false;
        this.isEmailIncorrect = false;
        return false;
      }
      else if (this.contactEmails.some(email => (/^[^\s@]+@Xmail\.com$/.test(email) === false))) {
        console.log('email');
        this.isEmpty = false;
        this.isRepeated = false;
        this.isEmailIncorrect = true;
        return false;
      }
      this.isEmpty = false;
      this.isRepeated = false;
      this.isEmailIncorrect = false;
      return true;
    },
    async saveContactChanges() {
      this.contactEmails = this.tempToStr.split(',');
      if (this.checkContactValues()) {
        let contact = {
          name: this.contactName,
          emails: this.contactEmails,
        };
        let contactToEdit = {
          contact,
          email: this.email,
          source: this.oldContactName,
          destination: this.contactName,
        };
        await fetch('http://localhost:8080/editContact', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(contactToEdit),
        }).then(response => {
          return response.json()
        }).then(data => {
          console.log(data);
          this.contacts = data;
          this.showableContacts = data;
          this.sortType = 'default';
          this.showTools.fill(false);
        })
          .catch((error) => {
            console.log(error + "error AAAAAAAAAAA");
          });
        this.closeCreate();
        this.contactName = '';
        this.contactEmails = [];
        this.tempToStr = '';
      }
    },
    editContact(contact, index) {
      this.contactName = contact.name,
        this.tempToStr = contact.emails.join(','),
        this.contactIndex = index,
        this.oldContactName = contact.name,
        this.editingContact = true;
    },
    async loadSystem() {
      await fetch("http://localhost:8080/getUser", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: this.email,
      })
        .then((response) => {
          return response.json();
        })
        .then((data) => {
          this.setDataToSystem(data);
          this.messages = this.inbox;
          console.log(this.inbox);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    openDraftComposeBox(draft) {
      this.draftToEdit = draft;
      this.composeBox = true;
      this.isMinimized = false;
    },
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
        this.selectAll = false
        this.handleSelectAll()
      }
    },
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
        this.selectAll = false
        this.handleSelectAll()
      }
    },
    closeCompose(xButton) {
      this.composeBox = !xButton;
      this.isDraft = false
    },
    searchContacts() {
      let searchContact = {
        email: this.email,
        source: this.searchText,
      };
      this.searchOrfilter = true;
      fetch("http://localhost:8080/searchContacts", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(searchContact),
      })
        .then((response) => {
          return response.json();
        })
        .then((data) => {
          this.showableContacts = data;
          console.log(data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    search() {
      if (this.pageToLoad === 'contacts') {
        this.searchContacts();
      }
      else {
        let hashMap = {
          subject: this.searchText,
          to: this.searchText,
          from: this.searchText,
          content: this.searchText,
          year: this.searchText,
          date: this.searchText,
          priority: this.searchText,
          email: this.email,
          attachmentName: this.searchText,
        };
        this.messagesToLoad(this.pageToLoad);
        let searchData = {
          hashMap,
          sourceMails: this.messages,
          email: this.email,
        }
        this.searchOrfilter = true;
        fetch("http://localhost:8080/search", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(searchData),
        })
          .then((response) => {
            return response.json();
          })
          .then((data) => {
            this.messages = data;
            console.log(data);
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
    filter() {
      let hashMap = {
        subject: this.subjectCri,
        to: this.toCri,
        from: this.fromCri,
        content: this.contentCri,
        year: this.yearCri,
        date: this.dateCri,
        priority: this.priorityCri,
        email: this.email,
        attachmentName: this.attachmentNameCri,
      };
      this.messagesToLoad(this.pageToLoad);
      let filterData = {
        hashMap,
        sourceMails: this.messages,
        email: this.email,
      };
      console.log(filterData);
      this.searchOrfilter = true;
      fetch("http://localhost:8080/filter", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(filterData),
      })
        .then((response) => {
          return response.json();
        })
        .then((data) => {
          this.messages = data;
          this.subjectCri = '',
            this.toCri = '',
            this.fromCri = '',
            this.contentCri = '',
            this.yearCri = '',
            this.dateCri = '',
            this.priorityCri = '',
            this.showSearchBox = false,
            this.attachmentNameCri = '',
            console.log(data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    handleButtonClicked(pageToLoad) {
      this.contactPage = false;
      this.currentMail = null;
      this.searchText = '';
      this.searchOrfilter = false;
      if (pageToLoad === "compose") {
        this.composeBox = true;
        this.isDraft = false;
        this.draftToEdit = null;
      } else {
        this.selectAll = false
        this.handleSelectAll()
        this.currentPage = 1;
        this.pageToLoad = pageToLoad;
        this.messagesToLoad(this.pageToLoad);
      }
      if (this.totalPages > 1) this.endOfPages = false;
      else this.endOfPages = true;
    },
    messagesToLoad(pageToLoad) {
      if (pageToLoad === "inbox") {
        this.messages = this.inbox;
      } else if (pageToLoad == "sent") {
        this.messages = this.sent;
      } else if (pageToLoad === "label") {
        this.labelBox = true;
      } else if (pageToLoad == "contacts") {
        this.showableContacts = this.contacts;
        console.log(this.showableContacts);
        this.contactPage = true;
      } else if (pageToLoad === "draft") {
        this.messages = this.drafts;
      }
      else if (pageToLoad === "trash") {
        this.messages = this.trash;
      }
      else {
        const selectedFolder = this.customFolders.find(folder => folder.folderName === pageToLoad);
        this.messages = selectedFolder.mail;
        console.log(this.messages);
      }
    },
    handleCollapse(collapse) {
      this.collapsed = collapse;
    },
    handleCheckboxChange(message) {
      if (message.selected) {
        this.selectedMessages.push(message);
      } else {
        let index = this.selectedMessages.indexOf(message);
        this.selectedMessages.splice(index, 1);
      }
    },
    async getCookie() {
      const cookieDecoded = decodeURIComponent(document.cookie);
      const cookieArray = cookieDecoded.split("; ");
      cookieArray.forEach((element) => {
        if (element.indexOf("email") == 0)
          this.email = element.substring("email".length + 1);
      });
    },
    closecreateFolder(xButton) {
      this.labelBox = !xButton;
    },
    refreshDraftArr(draftArr) {
      this.drafts = draftArr;
      if (this.pageToLoad === "draft") {
        this.messages = this.drafts;
        this.selectAll = false
        this.handleSelectAll()
      }
    },
    refreshAfterSend(sentArr, draftArr) {
      if (this.pageToLoad !== "inbox") {
        this.selectAll = false
        this.handleSelectAll()
      }
      this.sent = sentArr;
      this.drafts = draftArr
      if (this.pageToLoad === "sent") this.messages = this.sent;
      else if (this.pageToLoad === "draft") {
        if (this.drafts !== null)
          this.messages = this.drafts
        else
          this.messages = null
      };
    },
    checkCurrentMail(msg) {
      this.currentMail = msg
      if (this.currentMail.mailType === "draft") {
        this.isDraft = true
        this.composeBox = true
        this.draftToEdit = this.currentMail
      }
    },
    handleSelectAll() {
      this.showableMessages.forEach((msg) => (msg.selected = this.selectAll));
      this.selectedMessages = [...this.showableMessages];
      if (!this.selectAll) this.selectedMessages = [];
    },
    async moveMessagesToTrash(messages) {
      let system = {
        email: this.email,
        sourceMails: messages,
        destinationMails: [],
        source: this.pageToLoad,
        destination: "trash",
      };
      system = await this.move("trashMail", system);
      this.trash = system.destinationMails;
      if (this.pageToLoad === "inbox") {
        this.inbox = system.sourceMails;
      } else if (this.pageToLoad == "sent") {
        this.sent = system.sourceMails;
      } else if (this.pageToLoad === "draft") {
        this.drafts = system.sourceMails;
      } else if (this.pageToLoad === "trash") {
        this.trash = system.sourceMails;
      }

      // if(system.sourceMails.length === 0 ) 
      //   this.selectAll =false 

      this.messages = system.sourceMails;

      this.selectAll = false;
      this.handleSelectAll();
    },
    async move(path, system) {
      await fetch("http://localhost:8080/" + path, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(system),
      })
        .then((response) => {
          return response.json();
        })
        .then((data) => {
          system = data;
        })
        .catch((error) => {
          console.log(error);
        });
      return system;
    },
    async createContact() {
      this.contactEmails = this.tempToStr.split(',');
      if (this.checkContactValues()) {
        let contact = {
          name: this.contactName,
          emails: this.contactEmails,
        };
        let contactToAdd = {
          contact,
          email: this.email,
        };
        await fetch('http://localhost:8080/addContact', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(contactToAdd),
        }).then(response => {
          return response.json()
        }).then(data => {
          this.contacts = data;
          this.showableContacts = data;
          this.sortType = 'default';
          this.showTools.push(false);
          this.showTools.fill(false)
        })
          .catch((error) => {
            console.log(error + "error AAAAAAAAAAA");
          });
        this.closeCreate();
        this.contactName = '';
        this.contactEmails = [];
        this.tempToStr = '';
      }
    },
    closeCreate() {
      this.addContact = false;
      this.editingContact = false;
      this.isEmailIncorrect = false;
      this.isRepeated = false;
      this.isEmpty = false;
    },
    toggleTools(index) {
      // Toggle the showTools property for the clicked contact 
      this.showTools[index] = !this.showTools[index]
    },
    async restoreTrash(messages) {
      let system = {
        email: this.email,
        sourceMails: messages,
      };
      system = await this.move("restoreTrash", system);
      this.setDataToSystem(system);
      this.messages = this.trash;
      // console.log(system) 
    },
    async deleteFolder(folderName) {
      let deleteFolder = {
        source: folderName,
        email: this.email,
      };
      console.log(deleteFolder);
      await fetch('http://localhost:8080/removeFolder', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(deleteFolder),
      }).then(response => {
        return response.json()
      }).then(data => {
        this.customFolders = data;
      })
        .catch((error) => {
          console.log(error + "error AAAAAAAAAAA");
        });
    },
    async renameFolder(oldfolderName, folderName) {
      let renameFolder = {
        source: oldfolderName,
        destination: folderName,
        email: this.email,
      };
      console.log(renameFolder);
      await fetch('http://localhost:8080/renameFolder', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(renameFolder),
      }).then(response => {
        return response.json()
      }).then(data => {
        this.customFolders = data;
      })
        .catch((error) => {
          console.log(error + "error AAAAAAAAAAA");
        });
    },
    async getContactsSorted() {
      let sortInfo = {
        email: this.email,
        source: this.sortType
      }
      await fetch("http://localhost:8080/sortContacts", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(sortInfo),
      })
        .then((response) => {
          return response.json();
        })
        .then((data) => {
          console.log(data)
          this.showableContacts = data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    async getMessagesSorted() {
      if (this.sortType === "priority") {
        let sortInfo = {
          email: this.email,
          sourceMails: this.messages,
          source: this.sortType,
        };

        await fetch("http://localhost:8080/defaultOrPriority", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(sortInfo),
        })
          .then((response) => {
            return response.json();
          })
          .then((data) => {
            console.log(data);
            this.messages = data;
          })
          .catch((error) => {
            console.log(error);
          });
      } else {
        this.messagesToLoad(this.pageToLoad)
      }
    },
    async logOut() {
      this.deleteCookie();
      await fetch("http://localhost:8080/signOut", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: this.email,
      }).catch((error) => {
        console.log(error);
      });

      setTimeout(() => {
        this.$router.push("/");
      }, 1000);
    },
    deleteCookie() {
      let date = new Date();
      date.setTime(date.getTime() - 5 * 24 * 60 * 60 * 1000); //getTime returns time in milliseconds 
      let expires = "expires=" + date.toUTCString();
      console.log(document.cookie);
      document.cookie = `email=${this.email}; ${expires};`
      console.log(document.cookie);
    },
  },
};
</script>
<style scoped>
.delete-sellected {
  scale: 1.5;
  cursor: pointer;
  border-radius: 50%;
  padding: 5px;
  transition: 0.3s;
}

#MailTools {
  display: flex;
  min-width: 300px;
  justify-content: space-between;
  align-items: center;
  flex-direction: row;
}

#MailTools select {
  padding: 10px;
  cursor: pointer;
  transition: 0.3s;
}

#MailTools select:active {
  background-color: #29495A;
  color: white;
}

#MailTools select:hover {
  background-color: #29495A;
  color: white;
}

#MailTools select option {
  padding: 5px;
  background-color: white;
  color: black;
  transition: 0.3s;
}

#MailTools select option:hover {
  background-color: #29495A;
  color: white;
}

/* .restore {
  right: 50px;
} */

.sellect-all-box {
  margin-left: 10px;
  transform: scale(1.5);
}

.Content {
  display: flex;
  flex-direction: column;
  margin-left: 10px;
  width: 100%;
  overflow-y: hidden;
  height: 100vh;
}

.logo {
  background-color: blue;
  width: 25%;
}

.containsMessage {
  display: flex;
  flex-direction: column;
}

.header-tools {
  /*position: fixed;
  top: 8px;*/
  display: flex;
  margin-left: 260px;
  width: 50%;
  padding: 10px;
  background-color: #fff;
  border: none;
  height: 30px;
}

.home-container {
  display: flex;
  overflow-x: hidden;
  overflow-y: hidden;
  height: 100vh;
}

.navigate-pages {
  display: flex;
  justify-content: space-between;
  height: 50px;
  padding: 10px;
  top: 60px;
  /* position: fixed; */
  right: 20px;
  font-size: 14px;
  color: #59595c;
  align-content: center;
  flex-direction: row;
  align-items: center;
}

.navigate-pages-button {
  background-color: transparent;
  border: none;
  cursor: pointer;
  font-size: 18px;
}

.disabled-navigation {
  cursor: default;
  color: #888;
}

.search-box {
  /*position: absolute;
  top: 50px;
  left: 10px;*/
  padding: 10px;
  background-color: #fff;
  border: 1px solid #ccc;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.search-box input {
  width: 100%;
  padding: 8px;
  margin-bottom: 8px;
  box-sizing: border-box;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.search-box input::placeholder {
  color: #999;
}

.search-box input:focus {
  outline: none;
  border-color: #3498db;
  box-shadow: 0 0 5px rgba(52, 152, 219, 0.5);
}

.search-icon {
  cursor: pointer;
  border: solid 1px;
  width: 75%;
}

.inbox-container {
  overflow-y: scroll;
  /*width: 100%;
  margin-left: 260px;
  margin-top: 110px;*/
}

.message-item {
  position: relative;
  border: none;
  display: flex;
  border-bottom: solid 1px #e2dfdf;
  background-color: #fafafa;
  padding: 10px;
  cursor: pointer;
  height: 50px;
  overflow-x: hidden;
  transition: background-color 0.3s;
}

.message-item:hover {
  background-color: #dbdbdb;
}

.message-item .priority {
  position: absolute;
  left: -5px;
  top: -5px;
  font-size: 15px;
  border-radius: 50%;
  color: white;
  background: #29495A;
  width: 20px;
  padding: 5px;
}

.collapsed-messages {
  margin-left: 120px;
}

.message-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 5px;
  max-width: 73vw;
}

.message-from {
  font-weight: 20px;
  color: #363636;
}

.message-subject {
  display: flex;
  max-width: 100%;
}

.message-date {
  position: absolute;
  right: 50px;
  top: 35%;
  color: #888;
  font-size: 15px;
}

.message-body {
  margin-top: 5px;
  color: #444;
  margin-left: 0;
  display: flex;
  height: 50px;
  white-space: nowrap;
  overflow: hidden;
}

input.message-checkbox {
  width: 22px;
  height: 22px;
}

.select-box {
  display: flex;
  align-items: center;
  padding: 15px;
}

.message-checkbox {
  margin: 0;
}

.message-outside {
  display: flex;
  position: relative;
  flex-direction: column;
  width: calc(100% - 80px);
}

.contactContainer {
  text-align: left;
  padding: 20px;
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  background: #fff;
  margin: 20px;
  border-radius: 20px;
  box-shadow: 0px 0px 20px 6px rgba(0, 0, 0, 0.1);
  max-height: 100%;
}

.scrollContacts {
  max-height: 80vh;
  overflow-y: scroll;
}

.personalInfo {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.email {
  margin-top: 20px;
  display: flex;
  font-size: 20px;
  margin-bottom: 10px;
}

.email ul {
  min-width: 200px;
  font-size: 20px;
  padding: 0;
  margin: 0 10px 10px 0;
  background: white;
  border: 0.1px solid #EEE;
  list-style: none;
}

.email ul li {
  padding: 10px;
  cursor: pointer;
  transition: 0.3s;
}

.email ul li:hover {
  background: #EEE;
}

.contactTools {
  cursor: pointer;
  position: relative;
  transition: 0.3s;
  padding: 5px;
  transition: 0.3s;
  border-radius: 50%;
}

.contactTools:hover {
  background: #EEE;
}

.contactTools ul {
  list-style: none;
  position: absolute;
  right: 0;
  min-width: 100px;
}

.contactTools li {
  padding: 5px;
  transition: 0.3s;
}

.contactTools li:hover {
  background: #EEE;

}

.newContactButton {
  padding: 13px 20px;
  border-radius: 8px;
  margin: 20px 10px 0 25px;
  width: 25%;
  border: none;
  cursor: pointer;
  background: #29495A;
  color: white;
  font-weight: 400;
  transition: 0.3s;
}

.newContactButton:hover {
  background: #EEE;
  color: #29495A;
}

.search-box {
  padding: 10px;
  background-color: #fff;
  border: 1px solid #ccc;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  position: absolute;
}

.search-box {
  display: flex;
  flex-direction: column;
  z-index: 11;
}

.search-box div {
  padding: 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 18px;
  color: grey;
}

.search-box div {
  border-bottom: solid 0.1px #eee;
}

.inputBox {
  width: 300px;
  border: none;
  border-bottom: solid 0.1px #c0c0c0;
  height: 30px;
  color: grey;
}

.search-box div label {
  margin-right: 20px;
  cursor: pointer;
}

.search-box div:hover {
  background: #EEE;
  transition: 0.3s;
}

.search-box span {
  right: 0;
  top: 0;
  position: absolute;
  padding: 5px;
  color: grey;
  cursor: pointer;
  transition: 0.3s;
}

.search-box span:hover {
  border-radius: 50%;
  background: #EEE;
}

.search-icon {
  padding: 10px;
  width: 100%;
  height: 20px;
  border: none;
  border-radius: 20px;
  box-shadow: 0px 0px 6px 0px rgb(0 0 0 / 36%);
  background: #fff;
  position: relative;
  padding-left: 40px;
}

#searching {
  display: flex;
  position: relative;
  min-width: 100%;
}

.filterIcon {
  position: absolute;
  width: 30px;
  right: 0;
  top: 5px;
  transition: 0.3s;
}

.searchIcon {
  position: absolute;
  width: 30px;
  left: 10px;
  top: 5px;
  transition: 0.3s;
  z-index: 10;
}

#searching img:hover {
  background: rgb(198, 198, 198);
  border-radius: 50%;
  cursor: pointer;
}

.filterButton {
  display: flex;
  background: #29495a;
  text-align: center;
  cursor: pointer;
  color: white;
  padding: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 20px;
  font-weight: bold;
  transition: 0.3s;
}

.filterButton:hover {
  color: #29495a;
  background-color: #eee;
}

.create-contact-container {
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

.create-button {
  background-color: rgb(34, 62, 77);
  color: white;
}

.footer-tools {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}

.create-contact-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.create-contact-header span {
  font-size: 18px;
  font-weight: bold;
}

.create-contact-header button {
  background: none;
  border: none;
  font-size: 20px;
  cursor: pointer;
  color: #888;
  transition: color 0.3s ease;
}

.create-contact-header button:hover {
  color: #333;
}

.create-contact-container input {
  width: 100%;
  padding: 10px;
  margin-bottom: 15px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 14px;
  transition: border-color 0.3s ease;
}

.create-contact-container button {
  width: 100%;
  padding: 10px;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  transition: background-color 0.3s ease, color 0.3s ease;
}

.showEmails {
  position: absolute;
  left: 150px;
}

.showEmails img {
  width: 25px;
  cursor: pointer;
}

.move-button {
  cursor: pointer;
  padding: 10px;
  background-color: rgb(34, 62, 77);
  color: #fff;
  border: none;
  border-radius: 3px;
}

.move-box {
  z-index: 100;
  position: relative;
}

.select-container {
  position: absolute;
  margin-top: 10px;
}

::-webkit-scrollbar {
  cursor: pointer;
  width: 12px;
  /* Set the width of the scrollbar */
}

::-webkit-scrollbar-track {
  background-color: #f1f1f1;
  /* Set the color of the track (the area behind the scrollbar) */
}

::-webkit-scrollbar-thumb {
  background-color: #29495A;
  /* Set the color of the thumb (the draggable part of the scrollbar) */
  border-radius: 6px;
  /* Set the border radius of the thumb */
}

::-webkit-scrollbar-thumb:hover {
  background-color: #555;
  /* Set the color of the thumb on hover */
}

.custom-select {
  width: 150px;
  padding: 5px;
}

.select-container select {
  appearance: none;
  border: 1px solid #ddd;
  padding: 5px;
  border-radius: 3px;
  background-color: #fff;
}

img {
  width: 10px;
  margin-right: 12px;
}

.restore,
.trash {
  width: 15px;
}

.doOPeration-container {
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
.doOPeration-button {
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

.cancel-button,
.doOPeration-button {
  width: 100px;
  padding: 10px;
  text-align: center;
  cursor: pointer;
  transition: background-color 0.3s ease, color 0.3s ease;
  margin-right: 10px;
}


.doOPeration-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.doOPeration-header button {
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

.doOPeration-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

span,
.error-message {
  color: red;
  margin-top: 5px;
  font-size: 16px;
  font-weight: bold;
  text-align: center;
  display: block;
}

.three-dots {
  font-weight: bolder;
  font-size: 20px;
}
.message-tools{
  display:flex;
  align-items: center;
}
::-webkit-scrollbar { 
  cursor: pointer; 
  width: 12px; 
  /* Set the width of the scrollbar */ 
} 
 
::-webkit-scrollbar-track { 
  background-color: #f1f1f1; 
  /* Set the color of the track (the area behind the scrollbar) */ 
} 
 
::-webkit-scrollbar-thumb { 
  background-color: #29495A; 
  /* Set the color of the thumb (the draggable part of the scrollbar) */ 
  border-radius: 6px; 
  /* Set the border radius of the thumb */ 
} 
 
::-webkit-scrollbar-thumb:hover { 
  background-color: #555; 
  /* Set the color of the thumb on hover */ 
}
select { 
  padding: 10px; 
  cursor: pointer; 
  transition: 0.3s; 
} 
 
select:active { 
  background-color: #29495A; 
  color: white; 
} 
 
select:hover { 
  background-color: #29495A; 
  color: white; 
} 
 
select option { 
  padding: 5px; 
  background-color: white; 
  color: black; 
  transition: 0.3s; 
} 
 
select option:hover { 
  background-color: #29495A; 
  color: white; 
}
</style>