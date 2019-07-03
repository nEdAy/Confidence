package controller

import (
	"Shepherd/pkg/helper"
	"Shepherd/service"
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
	parameterMap := map[string]string{
		"rankType": ranking.RankType,
		"cid":      ranking.Cid,
	}
	cacheKey := c.Request.RequestURI
	rankingData, err := service.GetRanking(cacheKey, parameterMap)
	if err != nil {
		helper.ResponseErrorWithMsg(c, err.Error())
	} else {
		c.String(http.StatusOK, rankingData)
	}
}
