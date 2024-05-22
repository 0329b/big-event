<script lang="ts" setup>
import { ref } from 'vue'
import { FormProps, ElMessage } from 'element-plus'
import { userResetService } from '@/api/user'
import { useRouter } from 'vue-router'

const labelPosition = ref<FormProps['labelPosition']>('right')

const userFrom = ref({
  old_pwd: '',
  new_pwd: '',
  re_pwd: ''
})
//配置校验规则
const rules = {
  old_pwd: [
    { required: true, message: '请输入原密码', trigger: 'blur' },
    {
      pattern: /^\S{6,15}$/,
      message: '密码必须是6到15位非空字符',
      trigger: 'blur'
    }
  ],
  new_pwd: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    {
      pattern: /^\S{6,15}$/,
      message: '密码必须是6到15位非空字符',
      trigger: 'blur'
    }
  ],
  re_pwd: [
    { required: true, message: '请再次输入新密码', trigger: 'blur' },
    {
      pattern: /^\S{6,15}$/,
      message: '密码必须是6到15位非空字符',
      trigger: 'blur'
    },
    {
      validator: (rule, value, callback) => {
        //判断当前value是否与from收集的password一致
        if (value !== userFrom.value.new_pwd) {
          callback(new Error('两次密码不一致'))
        } else {
          callback()
        }
      }
    }
  ]
}
//清空表单
const clearFrom = () => {
  userFrom.value = {
    old_pwd: '',
    new_pwd: '',
    re_pwd: ''
  }
}
const router = useRouter()
//提交表单信息
const reset = async () => {
  let result = await userResetService(userFrom.value)
  if (result.code === 0) {
    ElMessage.success(result.mesg ? result.mesg : '重置密码成功,请重新登录')
    router.push('/login')
  } else {
    ElMessage.error(result.mesg ? result.mesg : '重置密码失败')
  }
}
</script>
<template>
  <el-card class="page-container" style="max-width: 100%">
    <template #header>
      <div class="card-header">
        <span><strong>重置密码</strong></span>
      </div>
    </template>
    <div style="margin: 20px" />
    <el-form
      :label-position="labelPosition"
      label-width="250px"
      :rules="rules"
      :model="userFrom"
      style="max-width: 600px"
    >
      <el-form-item label="原密码:" prop="old_pwd">
        <el-input v-model="userFrom.old_pwd" />
      </el-form-item>
      <el-form-item label="新密码:" prop="new_pwd">
        <el-input v-model="userFrom.new_pwd" />
      </el-form-item>
      <el-form-item label="确认新密码:" prop="re_pwd">
        <el-input v-model="userFrom.re_pwd" />
      </el-form-item>
    </el-form>
    <div style="margin: 40px" />
    <div class="button-container">
      <el-button type="default" @click="clearFrom()">重置</el-button>
      <el-button type="primary" style="margin-right: 450px" @click="reset()"
        >确认</el-button
      >
    </div>
  </el-card>
</template>
<style scoped>
.page-container {
  min-height: 100%;
  box-sizing: border-box;

  .card-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
}

.button-container {
  display: flex;
  justify-content: space-between;
  margin-left: 300px;
}
</style>
