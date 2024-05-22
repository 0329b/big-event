<script setup>
import { Delete, Edit } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'
// 假数据
const categorys = ref([
  {
    id: 5961,
    categoryName: '新的文章啊',
    createTime: '2022-07-10 14:53:52.604',
    updateTime: '2022-07-10 14:53:52.604',
    categoryAlias: '体育'
  },
  {
    id: 5962,
    categoryName: '新的文章啊',
    createTime: '2022-07-10 14:53:52.604',
    updateTime: '2022-07-10 14:53:52.604',
    categoryAlias: '体育'
  }
])
const state = ref('')
const categoryId = ref()
const articles = ref([
  {
    id: 5,
    title: '陕西',
    content: '宣布啊上次抱月听风v我却费用',
    coverImg: '',
    state: '草稿',
    categoryId: 2,
    createTime: '2022-07-10 14:53:52.604',
    updateTime: '2022-07-10 14:53:52.604'
  },
  {
    id: 6,
    title: '陕西',
    content: '宣布啊上次抱月听风v我却费用',
    coverImg: '',
    state: '草稿',
    categoryId: 2,
    createTime: '2022-07-10 14:53:52.604',
    updateTime: '2022-07-10 14:53:52.604'
  }
])
//分页数据模型
const pageNum = ref(1)
const total = ref(20)
const pageSizes = ref(3)

//每当也条数发生变化，调用此函数
const onSizeChange = (size) => {
  pageSizes.value = size
  getList()
}
//每当页码发生变化是
const onCurrentChange = (num) => {
  pageNum.value = num
  getList()
}
//获取文章分类数据
import {
  articleCategoryListService,
  articleListService,
  articleAddService,
  articleDeleteService,
  articleUpdateService
} from '@/api/article'
const getArticle = async () => {
  let result = await articleCategoryListService()
  categorys.value = result.data
}
getArticle()
//获取文章数据
const getList = async () => {
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSizes.value,
    categoryId: categoryId.value ? categoryId.value : null,
    state: state.value ? state.value : null
  }
  let result = await articleListService(params)
  articles.value = result.data.items
  total.value = result.data.total
  //查询对应的文章分类
  for (let i = 0; i < articles.value.length; i++) {
    let article = articles.value[i]
    for (let j = 0; j < categorys.value.length; j++) {
      if (article.categoryId === categorys.value[j].id) {
        article.categoryName = categorys.value[j].categoryName
      }
    }
  }
}
getList()
//文章添加
import { Plus } from '@element-plus/icons-vue'
//控制抽屉是否显示
const visibleDrawer = ref(false)
const articleModel = ref({
  title: '',
  categoryId: '',
  coverImg: '',
  content: '',
  state: ''
})
//token
import { userStore } from '@/stores'
import { ElMessage } from 'element-plus'
const userToken = userStore()
const uploadSuccess = (result) => {
  articleModel.value.coverImg = result.data
}
const title = ref('')
//发布文章
const addAticle = async (clickDate) => {
  //赋值给模型
  articleModel.value.state = clickDate
  //调用接口
  let result = await articleAddService(articleModel.value)
  ElMessage.success(result.mesg ? result.mesg : clickDate)
  //抽屉消失
  visibleDrawer.value = false
  //刷新数据列表
  getList()
}
//文章删除
const deleteArticle = (row) => {
  ElMessageBox.confirm('确认删除该数据吗?', '温馨提示', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(async () => {
      //调用删除接口
      await articleDeleteService(row.id)
      ElMessage({
        type: 'success',
        message: '删除成功'
      })
      getList()
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '取消删除'
      })
    })
}
//文章修改

//编辑函数
const showDailog = (row) => {
  title.value = '编辑文章'
  visibleDrawer.value = true
  articleModel.value.title = row.title
  articleModel.value.coverImg = row.coverImg
  articleModel.value.content = row.content
  articleModel.value.categoryId = row.categoryId
  articleModel.value.id = row.id
}
const updateArticle = async (clickDate) => {
  //赋值给模型
  articleModel.value.state = clickDate
  let result = await articleUpdateService(articleModel.value)
  ElMessage.success(result.mesg ? result.mesg : '修改成功')
  visibleDrawer.value = false
  getList()
}

//清空数据
const clearDate = () => {
  articleModel.value = {
    title: '',
    categoryId: '',
    coverImg: '',
    content: '',
    state: ''
  }
}
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>文章管理</span>
        <div class="extra">
          <el-button
            type="primary"
            @click="visibleDrawer = true ; clearDate() ; title='添加文章'"
            >添加文章</el-button
          >
        </div>
      </div>
    </template>
    <!-- 搜索表单 -->
    <el-form inline>
      <el-form-item label="文章分类：">
        <el-select
          v-model="categoryId"
          placeholder="请选择"
          style="width: 240px"
        >
          <el-option
            v-for="c in categorys"
            :key="c.id"
            :label="c.categoryName"
            :value="c.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="发布状态：">
        <el-select placeholder="请选择" v-model="state" style="width: 240px">
          <el-option label="已发布" value="已发布"></el-option>
          <el-option label="草稿" value="草稿"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getList">搜索</el-button>
        <el-button
          @click="
            categoryId = '';
            state = ''
          "
          >重置</el-button
        >
      </el-form-item>
    </el-form>
    <!-- 文章列表 -->
    <el-table :data="articles" style="width: 100%">
      <el-table-column
        label="文章标题"
        width="400"
        prop="title"
      ></el-table-column>
      <el-table-column label="分类" prop="categoryName"></el-table-column>
      <el-table-column label="发表时间" prop="createTime"> </el-table-column>
      <el-table-column label="状态" prop="state"></el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button
            :icon="Edit"
            circle
            plain
            type="primary"
            @click="showDailog(row)"
          ></el-button>
          <el-button
            :icon="Delete"
            circle
            plain
            type="danger"
            @click="deleteArticle(row)"
          ></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>
    <!-- 分页条 -->
    <el-pagination
      v-model:current-page="pageNum"
      v-model:page-size="pageSizes"
      :page-sizes="[3, 5, 10, 15]"
      layout="jumper,total,sizes,prev, pager, next"
      background
      :total="total"
      @size-change="onSizeChange"
      @current-change="onCurrentChange"
      style="margin-top: 20px; justify-content: flex-end"
    />
    <!-- 抽屉组件 -->
    <el-drawer
      v-model="visibleDrawer"
      :title="title"
      direction="rtl"
      size="50%"
    >
      <!-- 发表文章表单 -->
      <el-form :model="articleModel" label-width="100px">
        <el-form-item label="文章标题">
          <el-input
            v-model="articleModel.title"
            placeholder="请输入标题"
          ></el-input>
        </el-form-item>
        <el-form-item label="文章分类">
          <el-select v-model="articleModel.categoryId" width="100%">
            <el-option
              v-for="c in categorys"
              :key="c.id"
              :label="c.categoryName"
              :value="c.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="文章封面">
          <el-upload
            class="avatar-uploader"
            :auto-upload="true"
            :show-file-list="false"
            action="/api/upload"
            name="file"
            :headers="{ Authorization: userToken.token }"
            :on-success="uploadSuccess"
          >
            <img
              v-if="articleModel.coverImg"
              :src="articleModel.coverImg"
              class="avatar"
            />
            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
          </el-upload>
        </el-form-item>
        <el-form-item label="文章内容">
          <div class="editor">
            <quill-editor
              theme="snow"
              v-model:content="articleModel.content"
              contentType="html"
            >
            </quill-editor>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            @click="
              title === '添加文章'
                ? addAticle('已发布')
                : updateArticle('已发布')
            "
            >发布</el-button
          >
          <el-button
            type="info"
            @click="
              title  === '添加文章'
                ? addAticle('草稿')
                : updateArticle('草稿')
            "
            >草稿</el-button
          >
        </el-form-item>
      </el-form>
    </el-drawer>
  </el-card>
</template>
<style lang="scss" scoped>
.page-container {
  min-height: 100%;
  box-sizing: border-box;

  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
}
.avatar-uploader {
  :deep() {
    .avatar {
      width: 178px;
      height: 178px;
      display: block;
    }
    .el-upload {
      border: 1px dashed var(--el-border-color);
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
      transition: var(--el-transition-duration-fast);
    }
    .el-upload:hover {
      border-color: var(--el-color-primary);
    }
    .el-icon.avatar-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 178px;
      height: 178px;
      text-align: center;
    }
  }
}
.editor {
  width: 100%;
  :deep(.ql-editor) {
    min-height: 200px;
  }
}
</style>
