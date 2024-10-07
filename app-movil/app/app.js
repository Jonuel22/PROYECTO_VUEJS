import Vue from 'nativescript-vue';
import Login from './views/Login.vue'; // Página de inicio de sesión
import Home from './components/Home.vue'; // Página principal

Vue.config.silent = false; // Para ver advertencias

new Vue({
  data() {
    return {
      isAuthenticated: false, // Estado para el control de autenticación
    };
  },
  render(h) {
    return h('frame', [h(this.isAuthenticated ? Home : Login, {
      on: {
        loginSuccess: this.loginSuccess, // Pasamos el método como un evento a Login
      },
    })]);
  },
  methods: {
    loginSuccess() {
      this.isAuthenticated = true; // Autenticado
    },
    logout() {
      this.isAuthenticated = false; // Desautenticado
    },
  },
}).$start();
