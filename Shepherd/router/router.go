package router

import (
	"github.com/gin-gonic/gin"
	"github.com/nEdAy/wx_attendance_api_server/controller"
	"github.com/swaggo/gin-swagger"
	"github.com/swaggo/gin-swagger/swaggerFiles"
)

var Router *gin.Engine

func Setup() {
	Router = gin.Default()
	Router.Use(gin.Logger())
	Router.Use(gin.Recovery())
	// Router.Use(jwt.JWT())
	Router.Static("/assets", "./assets")
	Router.GET("/swagger/*any", ginSwagger.WrapHandler(swaggerFiles.Handler))
	// Ping test
	Router.GET("/ping", func(c *gin.Context) {
		c.String(200, "pong")
	})
	// v1
	v1 := Router.Group("/v1")
	{
		// 用户相关API
		user := v1.Group("/user")
		{
			// 微信小程序用户注册/登录 curl -X GET https://127.0.0.1/v1/user/token/ -d ""
			user.GET("/token/", controller.WeAppLogin)
			// 注册用户 curl -X POST https://127.0.0.1/v1/user/ -d ""
			user.POST("/", controller.Register)
			// 用户登录 curl -X POST https://127.0.0.1/v1/user/login/ -d ""
			user.POST("/login/", controller.Login)
			// 获取全部用户 curl -X GET  https://127.0.0.1/v1/user/
			user.GET("/", controller.UserList)
			// 删除用户 curl -X DELETE https://127.0.0.1/v1/user/login/1
			user.DELETE("/:id", controller.DelUser)
		}
		// COS相关API
		goods := v1.Group("/goods")
		{
			// 获取鉴权签名 curl -X GET  https://127.0.0.1/v1/cos/
			goods.GET("/", controller.GetAuthorization)
		}
	}
}
