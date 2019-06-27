package controller

import (
	"Shepherd/helper"
	"Shepherd/model"
	"github.com/gin-gonic/gin"
)

// @Summary 获取Banners
func GetBanners(c *gin.Context) {
	banners, err := model.GetAllBanner()
	if err != nil {
		helper.ResponseWithJsonError(c, err.Error())
	} else {
		helper.ResponseWithJsonData(c, banners)
	}
}
