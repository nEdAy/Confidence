package controller

import (
	"github.com/gin-gonic/gin"
)

func GetTop100(c *gin.Context) {
	getFromDataoke(c, Dataoke{
		c.Request.RequestURI,
		2 * 60,
		"https://openapi.dataoke.com/api/category/get-top100",
		map[string]string{}})
}