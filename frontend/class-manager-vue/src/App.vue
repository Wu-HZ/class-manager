<script setup lang="ts">
import { ref } from 'vue'; // 只需要 ref 来创建响应式数据
import request from './utils/request'; // 导入你封装的 Axios 实例

// 响应式变量，用于存储后端返回的消息
const helloMessage = ref('点击按钮获取后端消息...');
// 响应式变量，用于存储错误信息（如果发生）
const errorMessage = ref('');

/**
 * 异步函数，用于调用后端 /api/hello 接口
 */
const fetchHello = async () => {
  try {
    // 清空之前的错误信息
    errorMessage.value = '';
    // 使用封装的 request 实例发送 GET 请求到 /hello 路径
    // request.get() 会自动拼接 baseURL ('http://localhost:8080/api')
    const response = await request.get('/hello');
    // 假设后端直接返回字符串 "Hello from Spring Boot"
    helloMessage.value = response.data; // 将响应数据赋值给 helloMessage
  } catch (error: any) { // 使用 any 类型来处理各种可能的错误对象
    console.error('获取后端消息失败:', error);
    helloMessage.value = '获取失败！';
    // 尝试从错误对象中提取更具体的错误信息
    if (error.response) {
      // 服务器响应了错误状态码 (例如 401, 404, 500)
      errorMessage.value = `请求错误 ${error.response.status}: ${error.response.data?.message || error.response.statusText || '未知错误'}`;
      if (error.response.status === 401) {
        errorMessage.value += ' (未授权，请检查Spring Security配置或认证信息)';
      }
    } else if (error.request) {
      // 请求已发出但没有收到响应 (例如网络断开，CORS问题)
      errorMessage.value = '网络错误：请求没有收到响应，请检查后端是否运行或CORS配置。';
    } else {
      // 在设置请求时触发了错误
      errorMessage.value = `请求设置错误: ${error.message}`;
    }
  }
};

// 如果你希望页面加载时就自动获取消息，可以取消下面这行的注释
// import { onMounted } from 'vue';
// onMounted(() => {
//   fetchHello();
// });
</script>

<template>
  <div class="container">
    <h1>Vue 前端联调测试</h1>
    <p class="message">{{ helloMessage }}</p>
    <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
    <button @click="fetchHello" class="fetch-button">
      获取后端 "Hello" 消息
    </button>
  </div>
</template>

<style scoped>
/* 移除或修改原有的 header, logo, nav 等样式，因为我们移除了对应的元素 */
/* 专注于新的测试 UI 样式 */

.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh; /* 让容器至少占据整个视口高度 */
  padding: 20px;
  box-sizing: border-box;
  font-family: 'Inter', sans-serif; /* 使用 Inter 字体 */
  background-color: #f0f2f5; /* 轻微的背景色 */
}

h1 {
  color: #333;
  margin-bottom: 20px;
  font-size: 2.5rem;
  font-weight: 700;
  text-shadow: 1px 1px 2px rgba(0,0,0,0.1);
}

.message {
  font-size: 1.5rem;
  color: #555;
  margin-bottom: 30px;
  padding: 15px;
  background-color: #e0f7fa;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  min-width: 300px;
  text-align: center;
}

.error-message {
  font-size: 1.2rem;
  color: #d32f2f; /* 红色 */
  background-color: #ffebee; /* 浅红色背景 */
  padding: 10px 15px;
  border-radius: 8px;
  margin-bottom: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.08);
}

.fetch-button {
  padding: 15px 30px;
  font-size: 1.2rem;
  font-weight: 600;
  color: #fff;
  background-color: #42b983; /* Vue 绿色 */
  border: none;
  border-radius: 10px;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
  outline: none;
}

.fetch-button:hover {
  background-color: #36a172;
  transform: translateY(-3px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.3);
}

.fetch-button:active {
  transform: translateY(0);
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.2);
}

/* 响应式设计 */
@media (max-width: 768px) {
  h1 {
    font-size: 2rem;
  }
  .message {
    font-size: 1.2rem;
    min-width: unset;
    width: 90%;
  }
  .fetch-button {
    font-size: 1rem;
    padding: 12px 25px;
  }
}
</style>
