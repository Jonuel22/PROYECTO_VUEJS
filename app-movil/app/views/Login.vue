<template>
  <Page>
    <ActionBar title="Iniciar Sesión" backgroundColor="#007AFF" color="white" />
    <StackLayout class="login-container">
      <Label text="Bienvenido" class="welcome-text" />
      <TextField v-model="email" hint="Correo electrónico" class="input" keyboardType="email" autocorrect="false" autocapitalizationType="none" />
      <TextField v-model="password" hint="Contraseña" secure="true" class="input" />
      <Button text="Iniciar Sesión" @tap="login" class="btn-login" />
      <Label text="¿Olvidaste tu contraseña?" class="forgot-password" @tap="recoverPassword" />
    </StackLayout>
  </Page>
</template>

<script>
import MainMenu from './MainMenu.vue'; // Importa el menú principal
import * as appSettings from '@nativescript/core/application-settings'; // Importa el módulo

const API_URL = 'http://10.0.2.2:8080/login'; // URL de tu API

export default {
  data() {
    return {
      email: '',
      password: '',
      user: null, // Para almacenar la información del usuario después del login
    };
  },
  methods: {
    async login() {
      try {
        const response = await fetch(API_URL, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            email: this.email,
            password: this.password,
          }),
        });

        if (response.ok) {
          const data = await response.json();
          this.user = data; // Almacena los datos del usuario recibidos
          
          // Almacena el usuario en application-settings
          appSettings.setString('user', JSON.stringify(this.user));

          // Redirige al menú principal
          this.$navigateTo(MainMenu);
        } else {
          alert('Error de inicio de sesión');
        }
      } catch (error) {
        console.error('Error al iniciar sesión:', error);
        alert('Hubo un problema al iniciar sesión.');
      }
    },
    recoverPassword() {
      alert('Funcionalidad para recuperación de contraseña no implementada.');
    },
  },
  created() {
    // Recupera el usuario de application-settings si ya está autenticado
    const savedUser = appSettings.getString('user');
    if (savedUser) {
      this.user = JSON.parse(savedUser);
      this.$navigateTo(MainMenu); // Redirige si ya está autenticado
    }
  },
};
</script>

<style scoped>
.login-container {
  padding: 20;
  margin-top: 50;
  justify-content: center;
  align-items: center;
}

.welcome-text {
  font-size: 24;
  margin-bottom: 30;
  text-align: center;
  color: #333;
}

.input {
  margin-bottom: 10;
  padding: 10;
  border-radius: 5;
  border-color: #007AFF;
  border-width: 1;
  background-color: #f2f2f2;
}

.btn-login {
  background-color: #007AFF;
  color: white;
  padding: 10;
  border-radius: 5;
  margin-top: 20;
}

.forgot-password {
  margin-top: 20;
  text-align: center;
  color: #007AFF;
  text-decoration: underline;
}
</style>
