// src/axiosConfig.js
import axios from 'axios';

// Detecta si estás en un entorno de desarrollo (http) o producción (https)
const baseURL = window.location.protocol === 'https:' 
  ? 'https://tu-api-en-produccion.com'  // URL en producción (https)
  : 'http://localhost:8080';            // URL de desarrollo (http)

const apiClient = axios.create({
  baseURL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export default apiClient;
