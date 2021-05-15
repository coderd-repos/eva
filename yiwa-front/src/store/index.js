import { createStore } from 'vuex'
import testmodule from './modules/test-module'

export default createStore({
  state: {
    rootMessage: 'root message'
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    testmodule
  }
})
