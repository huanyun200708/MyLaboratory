//index.js
//获取应用实例
var app = getApp()
var inputValue = ''
var user = ''
Page({
  data: {
    motto: 'Hello World',
    userInfo: {},
    inputValue: ''
  },
  //事件处理函数
  bindViewTap: function() {
    wx.showModal({
      title: '提示',
      content: '这是一个模态弹窗',
      success: function(res) {
        if (res.confirm) {
          console.log('用户点击确定')
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
    })
  },
  bindKeyInput: function(e) {
      inputValue =  e.detail.value;
  },
  buttonEvent:  function(e)  {
    wx.showModal({
      content: "您的上车地点是："+ inputValue,
      success: function(res) {
        if (res.confirm) {
          wx.request({
          url: 'https://newone.xyz/myframework2/hq/savaOrUpdateAccount_login.do', 
          data: {
            address: inputValue ,
            user: user.nickName
          },
          header: {
              'content-type': 'application/json'
          },
          success: function(res) {
            wx.showToast({
            title: '申请成功，请等待...',
            icon: 'success',
            duration: 2000
          })
            console.log(res.data)
            sendSocketMessage(
              "{\"name\": \""+user.nickName+"\" ,\"address\": \""+inputValue+"\"}"
            )
          }
        })
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
    })
  },
  onLoad: function () {
    console.log('onLoad')
    var that = this
    //调用应用实例的方法获取全局数据
    app.getUserInfo(function(userInfo){
      user = userInfo;
      //更新数据
      that.setData({
        userInfo:userInfo
      })
    })
  }
})
var socketOpen = false
var socketMsgQueue = []
wx.connectSocket({
  url: 'wss://newone.xyz/myframework2/forwardWebSocket'
})

wx.onSocketOpen(function(res) {
  socketOpen = true
  socketMsgQueue = []
})

function sendSocketMessage(msg) {
  if (socketOpen) {
    wx.sendSocketMessage({
      data:msg
    })
  } else {
     socketMsgQueue.push(msg)
  }
}
wx.onSocketMessage(function(res) {
   wx.showToast({
            title: "您的上车地点是："+ res.data + ' 请等待...',
            icon: 'success',
            duration: 2000
          })
  console.log('收到服务器内容：' + res.data)
})