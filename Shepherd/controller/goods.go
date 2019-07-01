package controller

import (
	"Shepherd/helper"
	"Shepherd/util"
	"github.com/gin-gonic/gin"
	"gopkg.in/resty.v1"
)

func GetRanking(c *gin.Context) {
	rankType := c.Query("rankType")
	cid := c.Query("cid")
	parameterMap := map[string]string{
		"rankType": rankType,
		"cid":      cid,
	}
	ranking, err := resty.R().
		SetQueryParams(util.SignParameterMap(parameterMap)).
		Get("https://openapi.dataoke.com/api/goods/get-ranking-list")
	if err != nil {
		helper.ResponseWithJsonError(c, err.Error())
	} else {
		helper.ResponseWithJsonData(c, ranking.String())
	}
}
