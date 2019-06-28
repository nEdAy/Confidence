package router

import (
	"Shepherd/controller"
	_ "Shepherd/docs"
	"Shepherd/middleware/jwt"
	"github.com/gin-gonic/gin"
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
		// 注册用户 curl -X POST https://127.0.0.1/v1/auth/
		v1.POST("/register", controller.Register)
		// 用户登录 curl -X POST https://127.0.0.1/v1/user/login/
		v1.POST("/login", controller.Login)
		// 用户相关API
		user := v1.Group("/user", jwt.JWT())
		{
			// 获取用户 curl -X GET  https://127.0.0.1/v1/user/
			user.GET("/", controller.GetUser)
			// 删除用户 curl -X DELETE https://127.0.0.1/v1/user/login/1
			// user.DELETE("/:id", controller.DelUser)
		}
		// Banner相关API
		banner := v1.Group("/banner")
		{
			// 获取Banners curl -X GET  https://127.0.0.1/v1/banner/
			banner.GET("/", controller.GetBanners)
		}
		// 商品相关API
		goods := v1.Group("/goods")
		{
			// 获取各大榜单 curl -X GET  https://127.0.0.1/v1/good/ranking/
			goods.GET("/ranking", controller.GetRanking)
		}
	}
}
