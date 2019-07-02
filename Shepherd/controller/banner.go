package controller

import (
	"Shepherd/model"
	"Shepherd/pkg/helper"
	"github.com/gin-gonic/gin"
)

// @Summary 获取Banners
func GetBanners(c *gin.Context) {
	banners, err := model.GetAllBanner()
	if err != nil {
		helper.ResponseErrorWithMsg(c, err.Error())
	} else {
		helper.ResponseJsonWithData(c, banners)
	}
}
