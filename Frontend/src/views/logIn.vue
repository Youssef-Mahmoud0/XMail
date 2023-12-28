<template>
  <div class="login">
    <div class="container">
      <div class="login-box">
        <h2>Login</h2>
        <form @submit.prevent="login">
          <label for="email" class="child-left">Email</label>
          <input type="email" id="email" placeholder="john.doe@Xmail.com" v-model="email" />
          <p v-if="!isEmailValid" class="error-message">
            Please enter a valid Xmail address
          </p>
          <label for="password">Password</label>
          <input type="password" id="password" v-model="password">
          <div class="submit-button" @click="login">Login</div>
          <p v-if="isValid" class="success-message">Logged in Successfully</p>
          <p v-if="!isInputCorrect" class="error-message">
            Wrong Xmail or Password
          </p>
        </form>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'login',
  data() {
    return {
      email: '',
      password: '',
      isEmailValid: true,
      isInputCorrect: true,
      isValid: false
    };
  },
  methods: {
    validateEnteredEmail() {
      return this.isEmailValid = /^[^\s@]+@Xmail\.com$/.test(this.email);
    },
    async login() {
      let user = {
        email: this.email,
        password: this.password,
      };
      let fetchResult = false
      if (this.validateEnteredEmail()) {
        await fetch('http://localhost:8080/signIn', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(user),
        })
          .then(response => {
            return response.json();
          }).then(data => {
            fetchResult = data
          }).catch((error) => {
            // console.log(error+"error AAAAAAAAAAA"); 
          });

        if (fetchResult) {
          this.setCookie()
          this.isInputCorrect = true
          this.isValid = true
          setTimeout(() => {
            this.$router.push('/Home')
          }, 1500)
        }
        else {
          this.isInputCorrect = false
        }
      } else
        this.isEmailValid = false;
    },
    setCookie() {
      let date = new Date();
      date.setTime(date.getTime() + 5 * 24 * 60 * 60 * 1000); //getTime returns time in milliseconds 
      let expires = "expires=" + date.toUTCString();
      document.cookie = `email=${this.email}; ${expires};`
    },
  },

};
</script>
<style scoped>
.login {
  background-color: #f2f2f2;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
}

.submit-button {
  padding: 15px;
  border-radius: 5px;
  background: #29495A;
  color: white;
  font-weight: bold;
  border: none;
  cursor: pointer;
  transition: background 0.3s;
  margin-top: 20px;
}

.submit-button:hover {
  background: rgb(34, 62, 77);
}

.container {
  max-width: 400px;
  width: 100%;
}

.login-box {
  background-color: #ffffff;
  border: 1px solid #ccc;
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0px 0px 10px 0px #888888;
}

h2 {
  margin: 0;
  font-size: 24px;
  color: #333;
}

form {
  display: flex;
  flex-direction: column;
}

label {
  align-self: flex-start;
  font-size: 14px;
  color: #555;
  margin-top: 10px;
}

input {
  width: 100%;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
  box-sizing: border-box;
  margin-top: 5px;
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
}
</style>
