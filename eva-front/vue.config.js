// 详细配置请参考https://cli.vuejs.org/zh/config/#vue-config-js
module.exports = {
  publicPath: '/',
  outputDir: 'dist',
  assetsDir: 'static',
  lintOnSave: 'error',
  devServer: {
    host: '0.0.0.0',
    port: 10086,
    proxy: {
      [process.env.VUE_APP_API_PREFIX]: {
        target: 'http://localhost:10011',
        changeOrigin: true,
        pathRewrite: {
          [`^${[process.env.VUE_APP_API_PREFIX]}`]: ''
        }
      }
    }
  }
}
