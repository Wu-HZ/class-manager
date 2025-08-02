<template>
  <div class="home-container">
    <header class="home-header">
      <div class="avatar-section">
        <img class="avatar" src="https://i.pravatar.cc/80?img=3" alt="头像" />
      </div>
      <div class="header-actions">
        <select class="class-select" v-model="selectedClass" @change="handleClassChange">
          <option v-for="cls in classList" :key="cls.id" :value="cls.id">{{ cls.name }}</option>
        </select>
        <button class="create-class-btn" @click="showCreateClass = true">创建班级</button>
        <button class="logout-btn" @click="logout">退出登录</button>
      </div>
    </header>
    <main class="home-main">
      <nav class="side-menu">
        <ul>
          <li
            v-for="item in menuList"
            :key="item.key"
            :class="{ active: activeMenu === item.key }"
            @click="activeMenu = item.key"
          >
            <span class="menu-icon">{{ item.icon }}</span>
            <span>{{ item.label }}</span>
          </li>
        </ul>
      </nav>
      <section class="content-area">
        <component :is="activeComponent" v-if="!showCreateClass" />
        <div v-else class="create-class-form">
          <h2>创建班级</h2>
          <form @submit.prevent="submitClass">
            <div class="form-group">
              <label>班级号</label>
              <input v-model="newClass.id" type="text" required placeholder="请输入班级号" />
            </div>
            <div class="form-group">
              <label>年级</label>
              <select v-model="newClass.grade" required>
                <option value="" disabled>请选择年级</option>
                <option v-for="g in gradeList" :key="g" :value="g">{{ g }}</option>
              </select>
            </div>
            <div class="form-group">
              <label>班级</label>
              <select v-model="newClass.classNo" required>
                <option value="" disabled>请选择班级</option>
                <option v-for="n in classNoList" :key="n" :value="n">{{ n }}班</option>
                <option value="自定义">自定义</option>
              </select>
              <input
                v-if="newClass.classNo === '自定义'"
                v-model="newClass.customClassNo"
                type="text"
                placeholder="请输入自定义班级名"
                style="margin-top: 8px"
              />
            </div>
            <div class="form-group">
              <label for="teacher-select">
                班主任
                <button @click.prevent="fetchTeachers" class="btn-refresh">
                  <i class="fas fa-sync-alt">&#x21bb;</i>
                </button>
              </label>
              <select id="teacher-select" v-model="newClass.teacher" required>
                <option value="" disabled>请选择班主任</option>
                <option v-for="t in teacherList" :key="t.teacher_id" :value="t.teacher_id">
                  {{ t.teacher_name }}
                </option>
              </select>
            </div>
            <div class="form-group">
              <label>学生名单（每行一个学生姓名）</label>
              <textarea
                v-model="newClass.students"
                rows="6"
                placeholder="每行输入一个学生姓名"
              ></textarea>
            </div>
            <div class="form-actions">
              <button type="submit" class="submit-btn">提交</button>
              <button type="button" class="cancel-btn" @click="showCreateClass = false">
                取消
              </button>
            </div>
          </form>
        </div>
      </section>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'

// 假数据，实际可从后端获取
const classList = ref([
  { id: '1', name: '高一1班' },
  { id: '2', name: '高一2班' },
  { id: '3', name: '高一3班' },
])
const selectedClass = ref(classList.value[0].id)

const teacherList = ref([{ teacher_id: '1', teacher_name: '请刷新' }])

const menuList = [
  { key: 'overview', label: '班级概览', icon: '🏫' },
  { key: 'students', label: '学生管理', icon: '👨‍🎓' },
  { key: 'schedule', label: '课程安排', icon: '📅' },
  { key: 'score', label: '成绩录入', icon: '📝' },
  { key: 'notice', label: '通知公告', icon: '📢' },
]
const activeMenu = ref(menuList[0].key)

const router = useRouter()
const logout = () => {
  localStorage.removeItem('isLogin')
  localStorage.removeItem('token')
  router.push('/')
}
const handleClassChange = () => {
  // 可在此处请求后端获取对应班级数据
}

// 创建班级相关
const showCreateClass = ref(false)
const gradeList = [
  '一年级',
  '二年级',
  '三年级',
  '四年级',
  '五年级',
  '六年级',
  '七年级',
  '八年级',
  '九年级',
  '高一',
  '高二',
  '高三',
]
const classNoList = Array.from({ length: 20 }, (_, i) => i + 1)
const newClass = ref({
  id: '',
  grade: '',
  classNo: '',
  customClassNo: '',
  teacher: '',
  students: '',
})
const submitClass = () => {
  // 处理班级名
  let className = ''
  if (newClass.value.classNo === '自定义') {
    className = `${newClass.value.grade}${newClass.value.customClassNo}`
  } else {
    className = `${newClass.value.grade}${newClass.value.classNo}班`
  }
  // 处理学生名单，赋学号
  const studentsArr = newClass.value.students
    .split('\n')
    .map((s) => s.trim())
    .filter((s) => s)
    .map((name, idx) => ({
      stu_no_in_class: idx + 1,
      name: name,
      stu_class: newClass.value.id,
    }))
  // 打印提交信息
  const submitData = {
    class_id: newClass.value.id,
    class_name: className,
    class_grade: newClass.value.grade,
    class_no: newClass.value.classNo,
    class_head: newClass.value.teacher,
    class_students: studentsArr,
  }
  console.log('提交的班级信息:', submitData)
  alert('提交信息已打印到控制台')
  showCreateClass.value = false
  // 清空表单
  newClass.value = { id: '', grade: '', classNo: '', customClassNo: '', teacher: '', students: '' }
}

// 内容组件占位
const Overview = {
  template: `<div><h2>班级概览</h2><p>这里显示班级的基本信息和统计。</p></div>`,
}
const Students = {
  template: `<div><h2>学生管理</h2><p>这里可以管理学生信息。</p></div>`,
}
const Schedule = {
  template: `<div><h2>课程安排</h2><p>这里显示课程表。</p></div>`,
}
const Score = {
  template: `<div><h2>成绩录入</h2><p>这里可以录入和查看成绩。</p></div>`,
}
const Notice = {
  template: `<div><h2>通知公告</h2><p>这里显示班级通知。</p></div>`,
}

const activeComponent = computed(() => {
  switch (activeMenu.value) {
    case 'overview':
      return Overview
    case 'students':
      return Students
    case 'schedule':
      return Schedule
    case 'score':
      return Score
    case 'notice':
      return Notice
    default:
      return Overview
  }
})

const fetchTeachers = async () => {
  // 1. 从本地存储获取 token
  const token = localStorage.getItem('token')

  if (!token) {
    console.error('错误: 未找到本地存储中的 token')
    // 可选：重定向到登录页面
    // window.location.href = '/login';
    return
  }

  // 2. 构造请求头，携带 token
  const headers = new Headers()
  headers.append('Authorization', `Bearer ${token}`)

  // 3. 发送异步请求
  try {
    const response = await fetch('/api/teachers/list', {
      method: 'GET',
      headers: headers,
    })

    // 4. 检查响应状态码
    if (!response.ok) {
      // 如果状态码不是 2xx，抛出错误
      throw new Error(`网络请求失败，状态码: ${response.status}`)
    }

    // 5. 解析 JSON 数据
    const data = await response.json()
    console.log('教师列表已成功刷新:', data)

    // 6. 将数据赋值给 teacherList 变量
    teacherList.value = data
  } catch (error) {
    console.error('获取教师列表失败:', error)
    // 可选：在 UI 上显示错误消息
    // this.errorMessage = '无法获取教师数据，请稍后重试。';
  }
}
</script>

<style scoped>
.home-container {
  min-height: 100vh;
  width: 100vw;
  background: #f7f9fb;
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
  overflow-x: hidden;
}

.home-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1.2vw 2vw 0.8vw 2vw;
  background: #fff;
  box-shadow: 0 2px 8px rgba(60, 60, 60, 0.08);
  min-height: 70px;
  box-sizing: border-box;
  width: 100%;
}

.avatar-section {
  display: flex;
  align-items: center;
}

.avatar {
  width: 56px;
  height: 56px;
  border-radius: 50%;
  border: 2px solid #42b983;
  object-fit: cover;
  background: #e0f7fa;
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 1vw;
}

.class-select {
  padding: 8px 16px;
  border-radius: 8px;
  border: 1px solid #d0d7de;
  font-size: 1rem;
  background: #f0f2f5;
  outline: none;
  min-width: 120px;
}

.create-class-btn {
  padding: 8px 16px;
  margin-left: 10px;
  background: #fff;
  color: #42b983;
  border: 1px solid #42b983;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition:
    background 0.2s,
    color 0.2s;
}
.create-class-btn:hover {
  background: #42b983;
  color: #fff;
}

.logout-btn {
  padding: 8px 20px;
  background: linear-gradient(90deg, #42b983 60%, #36a172 100%);
  color: #fff;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: background 0.2s;
  min-width: 100px;
  margin-left: 10px;
}

.logout-btn:hover {
  background: #388e3c;
}

.home-main {
  flex: 1;
  display: flex;
  min-height: 0;
  box-sizing: border-box;
  width: 100%;
}

.side-menu {
  min-width: 140px;
  max-width: 200px;
  width: 15vw;
  background: #fff;
  box-shadow: 2px 0 8px rgba(60, 60, 60, 0.05);
  padding: 2vw 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  box-sizing: border-box;
}

.side-menu ul {
  list-style: none;
  padding: 0;
  margin: 0;
  width: 100%;
}

.side-menu li {
  width: 100%;
  padding: 16px 0 16px 24px;
  font-size: 1.1rem;
  color: #333;
  cursor: pointer;
  display: flex;
  align-items: center;
  border-left: 4px solid transparent;
  transition:
    background 0.2s,
    border-color 0.2s;
  box-sizing: border-box;
}

.side-menu li.active {
  background: #e0f7fa;
  border-left: 4px solid #42b983;
  color: #42b983;
}

.menu-icon {
  margin-right: 12px;
  font-size: 1.3rem;
}

.content-area {
  flex: 1;
  padding: 2vw 3vw;
  background: #f7f9fb;
  overflow-y: auto;
  min-width: 0;
  box-sizing: border-box;
}

.create-class-form {
  max-width: 480px;
  margin: 0 auto;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 2px 12px rgba(60, 60, 60, 0.08);
  padding: 32px 28px 24px 28px;
}

.create-class-form h2 {
  text-align: center;
  margin-bottom: 24px;
  color: #42b983;
}

.form-group {
  margin-bottom: 18px;
  display: flex;
  flex-direction: column;
}

.form-group label {
  margin-bottom: 8px;
  color: #333;
  font-weight: 500;
}

.form-group input,
.form-group select,
.form-group textarea {
  padding: 10px;
  border: 1px solid #d0d7de;
  border-radius: 6px;
  font-size: 1rem;
  outline: none;
  resize: none;
}

.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 16px;
  margin-top: 18px;
}

.submit-btn {
  background: #42b983;
  color: #fff;
  border: none;
  border-radius: 6px;
  padding: 8px 22px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: background 0.2s;
}

.submit-btn:hover {
  background: #388e3c;
}

.cancel-btn {
  background: #fff;
  color: #42b983;
  border: 1px solid #42b983;
  border-radius: 6px;
  padding: 8px 22px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition:
    background 0.2s,
    color 0.2s;
}

.cancel-btn:hover {
  background: #42b983;
  color: #fff;
}

/* 响应式优化：窄屏下侧边栏收窄，内容区填满 */
@media (max-width: 900px) {
  .side-menu {
    min-width: 80px;
    width: 20vw;
    padding: 1vw 0;
  }
  .content-area {
    padding: 2vw 1vw;
  }
  .home-header {
    flex-direction: column;
    align-items: flex-start;
    padding: 2vw 2vw 1vw 2vw;
  }
  .header-actions {
    margin-top: 10px;
    gap: 8px;
  }
}
</style>
