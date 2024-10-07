import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    user: null, // Almacena la información del usuario
  },
  mutations: {
    // Mutación para establecer el usuario
    setUser(state, user) {
      state.user = user;
    },
  },
  actions: {
    // Acción para guardar la información del usuario
    setUser({ commit }, user) {
      commit('setUser', user);
    },
  },
  getters: {
    // Getter para obtener el usuario
    getUser(state) {
      return state.user;
    },
  },
});
