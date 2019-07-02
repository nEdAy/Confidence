package controller

import (
	"Shepherd/pkg/helper"
	"Shepherd/pkg/redis"
	"Shepherd/util"
	"github.com/gin-gonic/gin"
	"gopkg.in/resty.v1"
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
	if redis.Exists(cacheKey) {
		data, err := redis.Get(cacheKey)
		if err != nil {
			getRankingSource(c, parameterMap, cacheKey)
		} else {
			c.String(http.StatusOK, string(data))
		}
	} else {
		getRankingSource(c, parameterMap, cacheKey)
	}
}

func getRankingSource(c *gin.Context, parameterMap map[string]string, cacheKey string) {
	rankingData, err := resty.R().
		SetQueryParams(util.SignParameterMap(parameterMap)).
		Get("https://openapi.dataoke.com/api/goods/get-ranking-list")
	if err != nil {
		helper.ResponseErrorWithMsg(c, err.Error())
	} else {
		if rankingData.IsSuccess() {
			c.String(http.StatusOK, rankingData.String())
			_ = redis.Set(cacheKey, rankingData.String(), 2)
		} else {
			helper.ResponseErrorWithMsg(c, "大淘客API异常")
		}
	}
}
