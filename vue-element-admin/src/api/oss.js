import request from '@/utils/request'


export default {
    //1.（查询分页）
    uploadOssFile(file) {
      return request({
        url: `/oss/fileoss/uploadOssFile`,
        method: "post",
        data: file
      })
    },
  }
  
