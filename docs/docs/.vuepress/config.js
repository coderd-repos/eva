module.exports = {
  title: 'Eva（单应用版）',
  head: [
    ['link', { rel: 'icon', href: '/logo.png' }]
  ],
  description: 'Just playing around',
  markdown: {
    lineNumbers: true,
    toc: {
      includeLevel: [1, 2, 3],
    },
  },
  themeConfig: {
    logo: '/logo.png',
    sidebarDepth: 2,
    sidebar: [
      {
        title: '开始',   // 必要的
        collapsable: false, // 可选的, 默认值是 true,
        sidebarDepth: 1,    // 可选的, 默认值是 1
        children: [
          '/guide/introduce',
          '/guide/about-us'
        ]
      },
      {
        title: '前端',   // 必要的
        collapsable: false, // 可选的, 默认值是 true,
        sidebarDepth: 1,    // 可选的, 默认值是 1
        children: [
          '/front/start',
          '/front/doc',
          '/front/gen',
          '/front/deploy',
          '/front/function'
        ]
      },
      {
        title: '后端',   // 必要的
        collapsable: false, // 可选的, 默认值是 true,
        sidebarDepth: 1,    // 可选的, 默认值是 1
        children: [
          '/server/start',
          '/server/doc',
          '/server/gen',
          '/server/deploy',
          '/server/function'
        ]
      },
      // '/front',
      // '/server'
    ]
  }
}
