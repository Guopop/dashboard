import axios from 'axios'

const http = axios.create({
  baseURL: 'http://localhost:8843'
})

http.interceptors.request.use((config) => {
  const token = window.sessionStorage.getItem('token')
  if (token !== null) {
    config.headers.Authorization = 'Bearer ' + token
  }
  return config
})

export default http
