package controller

import (
	"Shepherd/pkg/helper"
	"github.com/gin-gonic/gin"
	"net/http"
)

type rankingList struct {
	RankType string `form:"rankType" binding:"required"`
	Cid      string `form:"cid" `
}

func GetRankingList(c *gin.Context) {
	var rankingList rankingList
	if err := c.ShouldBindQuery(&rankingList); err != nil {
		helper.ResponseErrorWithMsg(c, err.Error())
		return
	}
	rankingData, err := GetDataByCacheOrSource(Dataoke{
		c.Request.RequestURI,
		2 * 60,
		"https://openapi.dataoke.com/api/goods/get-ranking-list",
		map[string]string{
			"rankType": rankingList.RankType,
			"cid":      rankingList.Cid,
		}})
	if err != nil {
		helper.ResponseErrorWithMsg(c, err.Error())
	} else {
		c.String(http.StatusOK, rankingData)
	}
}

type nineOpGoodsList struct {
	pageSize string `form:"pageSize" binding:"required"`
	pageId   string `form:"pageId" binding:"required"`
	cid      string `form:"cid" binding:"required"`
}

func GetNineOpGoodsList(c *gin.Context) {
	var nineOpGoodsList nineOpGoodsList
	if err := c.ShouldBindQuery(&nineOpGoodsList); err != nil {
		helper.ResponseErrorWithMsg(c, err.Error())
		return
	}
	rankingData, err := GetDataByCacheOrSource(Dataoke{
		c.Request.RequestURI,
		2 * 60,
		"https://openapi.dataoke.com/api/goods/nine/op-goods-list",
		map[string]string{
			"pageSize": nineOpGoodsList.pageSize,
			"pageId":   nineOpGoodsList.pageId,
			"cid":      nineOpGoodsList.cid,
		}})
	if err != nil {
		helper.ResponseErrorWithMsg(c, err.Error())
	} else {
		c.String(http.StatusOK, rankingData)
	}
}
