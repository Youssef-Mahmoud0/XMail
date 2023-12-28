import { createRouter, createWebHistory } from 'vue-router'
import signUp from '../views/signUp.vue'
import logIn from '../views/logIn.vue'
import Home from '../views/Home.vue'
const routes = [
  {
    path: '/',
    name: 'signup',
    component: signUp,
  },
  {
    path: '/logIn',
    name: 'login',
    component: logIn,
  },
  {
    path: '/Home',
    name: 'home',
    component: Home,
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
