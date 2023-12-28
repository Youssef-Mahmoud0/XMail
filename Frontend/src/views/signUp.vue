<template>
  <div class="signup">
    <div class="container">
      <div class="Sign-up">
        <div class="box">
          <h2>Create a new account</h2>
          <router-link to="/logIn"><p class="login-button">Already have an account? Log in here</p></router-link>
        </div>
        <form @submit.prevent="submitForm">
          <div class="flex-container">
            <div class="flex-item">
              <label for="firstName">First Name</label>
              <input type="text" id="firstName" placeholder="John" v-model="firstName" />
              <p v-if="!isFirstNameValid" class="error-message">
                Please enter a valid first name
              </p>
            </div>
            <div class="flex-item">
              <label for="lastName">Last Name</label>
              <input type="text" id="lastName" placeholder="Doe" v-model="lastName" />
              <p v-if="!isLastNameValid" class="error-message">
                Please enter a valid last name
              </p>
            </div>
          </div>
          <label for="email" class="child-left">Email</label>
          <input type="email" id="email" placeholder="john.doe@Xmail.com" v-model="email" />
          <p v-if="!isEmailValid" class="error-message">
            Please enter a valid email address
          </p>

          <label for="password" class="child-left">Password</label>
          <input type="password" id="password" placeholder="password" v-model="password" />
          <p v-if="!isPasswordValid" class="error-message">
            Password must be at least 8 characters long
          </p>

          <label for="confirmPassword" class="child-left">Confirm Password</label>
          <input type="password" id="confirmPassword" placeholder="confirm password" v-model="confirmPassword" />
          <p v-if="!isPasswordMatch" class="error-message">
            Passwords do not match
          </p>

          <label for="dob" class="child-left">Date of Birth</label>
          <input type="date" id="dob" v-model="dob" />
          <p v-if="!isDobValid" class="error-message">
            Please enter a valid date of birth greater than 18
          </p>

          <div class="Sign-up-button" @click="submitForm">Sign-up</div>
          <p v-if="valid" class="success-message">Sign up Successfully</p>
          <p v-if="isAlreadyExist" class="error-message">This Xmail already exists</p>
        </form>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "signup",
  data() {
    return {
      firstName: "",
      lastName: "",
      email: "",
      password: "",
      confirmPassword: "",
      dob: "",
      isFirstNameValid: true,
      isLastNameValid: true,
      isUsernameValid: true,
      isEmailValid: true,
      isPasswordValid: true,
      isPasswordMatch: true,
      isDobValid: true,
      isAlreadyExist: false,
      valid: false,
      currentUser: {},
    };
  },
  methods: {
    async submitForm() { 
      this.validateForm(); 
      if (this.isFormValid()) { 
        // Your form submission logic here 
        let user = { 
          firstName: this.firstName, 
          lastName: this.lastName, 
          email: this.email, 
          password: this.password, 
          dob: this.dob, 
        }; 
        let fetchResult = false 
        await fetch('http://localhost:8080/signUp',{ 
          method:'POST', 
          headers:{ 
            'Content-Type':'application/json', 
          }, 
          body: JSON.stringify(user), 
        }) 
        .then(response =>{ 
          return response.json(); 
        }).then(data =>{ 
          fetchResult = data 
        }).catch((error)=>{ 
        console.log(error+"error AAAAAAAAAAA"); 
        }); 
 
        if(fetchResult){ 
          this.setCookie() 
          setTimeout(() => { 
            this.$router.push('/Home') 
          },1500) 
          this.valid = true 
        } 
        else{ 
          this.valid = false 
          this.isAlreadyExist = true 
        } 
      } 
    },
    validateForm() {
      // Your form validation logic here
      this.isFirstNameValid = this.firstName !== "";
      this.isLastNameValid = this.lastName !== "";
      this.isEmailValid = /^[^\s@]+@Xmail\.com$/.test(this.email);
      this.isPasswordValid =
        this.password.length >= 8 && this.password.length <= 20;
      this.isPasswordMatch = this.password === this.confirmPassword;
      this.isDobValid = this.validateDob;
    },
    isFormValid() {
      // Check if all individual validations pass
      return (
        this.isFirstNameValid &&
        this.isLastNameValid &&
        this.isEmailValid &&
        this.isPasswordValid &&
        this.isPasswordMatch &&
        this.isDobValid
      );
    },
    setCookie(){ 
      let date = new Date(); 
      date.setTime(date.getTime() + 5 * 24* 60 * 60 * 1000 ); //getTime returns time in milliseconds 
      let expires = "expires=" + date.toUTCString(); 
      document.cookie = `email=${this.email}; ${expires};`  
    },
  },
  computed: {
    validateDob() {
      const currentDate = new Date();
      const selectedDate = new Date(this.dob);
      const minAgeDate = new Date();
      minAgeDate.setFullYear(minAgeDate.getFullYear() - 18);

      return (this.dob !== "" &&
        selectedDate <= currentDate &&
        selectedDate <= minAgeDate);
    }
  },
};
</script>
  
<style scoped>
.signup {
  display: flex;
  /* background-image: url("../assets/sidebar-icons/background7.jpg"); */
  height: 100%;
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover; 

  align-items: center;
  justify-content: center;
}

h2{
 color: #29495A;
}
.container {
  max-width: 800px;
  width: 100%;
  margin-top:60px;
  margin-bottom: 60px;
}

.Sign-up {
  background-color: #ffffff;
  border: 1px solid #ccc;
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0px 0px 10px 0px #888888;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.flex-container {
  display: flex;
  justify-content: space-between;
}

.flex-item {
  width: calc(48% - 10px);
  /* Adjust the width as needed, considering margin */
}

.box {
  margin-bottom: 20px;
  text-align: center;
}

h2 {
  margin: 0;
  font-size: 24px;
  color: #333;
}

.form-group {
  width: 100%;
  display: flex;
  flex-direction: column;
  margin-bottom: 15px;
}

.form-row {
  display: flex;
  justify-content: space-between;
}

.form-row .form-group {
  width: calc(33.33% - 10px);
  /* Adjust the width as needed */
}

label {
  align-self: flex-start;
  font-size: 14px;
  color: #555;
  margin-top: 10px;
  font-weight: bold;
}

input {
  width: 100%;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
  box-sizing: border-box;
  margin-top: 5px;
  margin-bottom: 10px;
  /* Add margin at the bottom of each input for spacing */
}

.Sign-up-button {
  /* width: 100%; */
  padding: 15px;
  border-radius: 5px;
  background: #29495A;
  color: white;
  font-weight: bold;
  border: none;
  cursor: pointer;
  transition: background 0.3s;
  margin-top: 20px;
  /* Adjust the top margin to move the button down */
}

.Sign-up-button:hover {
  background: rgb(34, 62, 77);
}

p.form-p {
  cursor: pointer;
  color: #007bff;
  font-size: 14px;
  text-decoration: underline;
  margin-top: 15px;
}

p.form-p:hover {
  color: #0056b3;
}

p.error-message {
  color: red;
  margin-top: 5px;
  font-size: 14px;
}

p.success-message {
  color: #29495A;
  margin-top: 5px;
  font-size: 14px;
}n
.login-button {
    color:#29495A;
    text-decoration: none;
    cursor: pointer; 
    font-size: 18x; 
    margin-top: 15px; 
  }

  .login-button:hover {
    color: rgb(34, 62, 77); 
  }
</style>

