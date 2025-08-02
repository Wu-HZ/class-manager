<template>
  <div class="login-container">
    <h2 class="login-title">教师登录</h2>
    <form class="login-form" @submit.prevent="handleLogin">
      <div class="form-group">
        <label for="teacherID">教师工号</label>
        <input
          id="teacherID"
          v-model="teacherID"
          type="text"
          placeholder="请输入教师工号"
          required
        />
      </div>
      <div class="form-group">
        <label for="teacher_password">密码</label>
        <input
          id="teacher_password"
          v-model="teacherPassword"
          type="password"
          placeholder="请输入密码"
          required
        />
      </div>
      <button class="login-btn" :disabled="loading">
        {{ loading ? '登录中...' : '登录' }}
      </button>
      <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>
      <div v-if="successMessage" class="success-message">{{ successMessage }}</div>
    </form>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const teacherID = ref('')
const teacherPassword = ref('')
const errorMessage = ref('')
const successMessage = ref('')
const loading = ref(false)
const router = useRouter()

const handleLogin = async () => {
  errorMessage.value = ''
  successMessage.value = ''
  loading.value = true
  try {
    const response = await axios.post('/api/login', null, {
      params: {
        teacherID: teacherID.value,
        teacher_password: teacherPassword.value,
      },
    })
    if (response.data.success) {
      localStorage.setItem('isLogin', 'true') // 保存登录状态
      localStorage.setItem('token', response.data.token) // 保存后端返回的 token
      successMessage.value = response.data.msg || '登录成功，正在跳转...'
      setTimeout(() => {
        router.push('/home')
      }, 1000)
    } else {
      errorMessage.value = response.data
    }
  } catch (error: any) {
    errorMessage.value = '网络或服务器错误，请稍后再试'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-container {
  max-width: 400px;
  margin: 80px auto;
  padding: 40px 32px 32px 32px;
  background: #fff;
  border-radius: 16px;
  box-shadow: 0 8px 32px rgba(60, 60, 60, 0.15);
  display: flex;
  flex-direction: column;
  align-items: center;
}

.login-title {
  font-size: 2rem;
  font-weight: 700;
  color: #42b983;
  margin-bottom: 32px;
  letter-spacing: 2px;
}

.login-form {
  width: 100%;
  display: flex;
  flex-direction: column;
}

.form-group {
  margin-bottom: 24px;
  display: flex;
  flex-direction: column;
}

label {
  font-size: 1rem;
  color: #333;
  margin-bottom: 8px;
}

input {
  padding: 12px;
  font-size: 1rem;
  border: 1px solid #d0d7de;
  border-radius: 8px;
  outline: none;
  transition: border-color 0.2s;
}

input:focus {
  border-color: #42b983;
}

.login-btn {
  padding: 14px 0;
  font-size: 1.1rem;
  font-weight: 600;
  color: #fff;
  background: linear-gradient(90deg, #42b983 60%, #36a172 100%);
  border: none;
  border-radius: 8px;
  cursor: pointer;
  margin-top: 8px;
  transition: background 0.2s;
}

.login-btn:disabled {
  background: #b2dfdb;
  cursor: not-allowed;
}

.error-message {
  margin-top: 18px;
  color: #d32f2f;
  background: #ffebee;
  padding: 10px 16px;
  border-radius: 8px;
  font-size: 1rem;
  text-align: center;
}

.success-message {
  margin-top: 18px;
  color: #388e3c;
  background: #e8f5e9;
  padding: 10px 16px;
  border-radius: 8px;
  font-size: 1rem;
  text-align: center;
}
</style>
