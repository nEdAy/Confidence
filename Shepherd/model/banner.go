package model

import (
	"github.com/jinzhu/gorm"
)

// Banner表
type Banner struct {
	Model
	Title    string `gorm:"column:title" json:"title"`
	PicURL   string `gorm:"column:picURL" json:"picURL"`
	ClickURL string `gorm:"column:clickURL" json:"clickURL"`
}

// TableName 返回banner表名称
func (Banner) TableName() string {
	return gorm.DefaultTableNameHandler(nil, "banner")
}

// GetAllBanner retrieves all Banner matches certain condition. Returns empty list if no records exist
func GetAllBanner() (banners []*Banner, err error) {
	if err = DB.Order("id desc").Find(&banners).Error; err == nil {
		return banners, nil
	}
	return nil, err
}
