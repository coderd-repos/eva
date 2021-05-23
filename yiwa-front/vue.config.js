// 详细配置请参考https://cli.vuejs.org/zh/config/#vue-config-js
module.exports = {
  publicPath: '/',
  outputDir: 'dist',
  assetsDir: 'static',
  devServer: {
    host: '0.0.0.0',
    port: 8081,
    proxy: {
      [process.env.VUE_APP_API_PREFIX]: {
        target: 'http://localhost:8082',
        changeOrigin: true,
        pathRewrite: {
          [`^${[process.env.VUE_APP_API_PREFIX]}`]: ''
        }
      }
    }
  }
}
