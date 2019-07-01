package controller

import (
	"Shepherd/helper"
	"Shepherd/redis"
	"Shepherd/util"
	"github.com/gin-gonic/gin"
	"github.com/gin-gonic/gin/binding"
	"gopkg.in/resty.v1"
	"net/http"
)

type ranking struct {
	RankType string `form:"rankType" binding:"required"`
	Cid      string `form:"cid" `
}

func GetRanking(c *gin.Context) {
	ranking := new(ranking)
	if err := c.ShouldBindWith(&ranking, binding.Query); err != nil {
		helper.ResponseWithJsonError(c, err.Error())
		return
	}
	parameterMap := map[string]string{
		"rankType": ranking.RankType,
		"cid":      ranking.Cid,
	}
	cacheKey := c.Request.RequestURI + ranking.RankType + "_" + ranking.Cid
	if redis.Exists(cacheKey) {
		data, err := redis.Get(cacheKey)
		if err != nil {
			helper.ResponseWithJsonError(c, err.Error())
		} else {
			c.String(http.StatusOK, string(data))
		}
	} else {
		rankingData, err := resty.R().
			SetQueryParams(util.SignParameterMap(parameterMap)).
			Get("https://openapi.dataoke.com/api/goods/get-ranking-list")
		if err != nil {
			helper.ResponseWithJsonError(c, err.Error())
		} else {
			_ = redis.Set(cacheKey, rankingData.String(), 2)
			c.String(http.StatusOK, rankingData.String())
		}
	}
}
