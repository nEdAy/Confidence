package jwt

import (
	"Shepherd/helper"
	"Shepherd/util"
	"github.com/gin-gonic/gin"
)

func JWT() gin.HandlerFunc {
	return func(c *gin.Context) {
		token := c.Request.Header.Get("Authorization")
		if token == "" {
			helper.ResponseWithJsonError(c, "Token不存在")
			c.Abort()
			return
		} else {
			claims, err := util.ParseToken(token)
			if err != nil {
				helper.ResponseWithJsonError(c, err.Error())
				c.Abort()
				return
			} else {
				c.Set("username", claims.Username)
				c.Next()
			}
		}
	}
}
