package controller

import (
	"Shepherd/pkg/helper"
	"Shepherd/service"
	"github.com/fatih/structs"
	"github.com/gin-gonic/gin"
	"net/http"
)

type ranking struct {
	RankType string `form:"rankType" binding:"required"`
	Cid      string `form:"cid" `
}

func GetRanking(c *gin.Context) {
	var ranking ranking
	if err := c.ShouldBindQuery(&ranking); err != nil {
		helper.ResponseErrorWithMsg(c, err.Error())
		return
	}
	cacheKey := c.Request.RequestURI
	rankingData, err := service.GetRanking(cacheKey, structs.Map(ranking))
	if err != nil {
		helper.ResponseErrorWithMsg(c, err.Error())
	} else {
		c.String(http.StatusOK, rankingData)
	}
}
