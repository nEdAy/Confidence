package model

import (
	"github.com/jinzhu/gorm"
)

// User 用户表
type User struct {
	Model
	Username string `gorm:"column:username" json:"username"`
	Password string `gorm:"column:password" json:"password"`
	Token    string `gorm:"-" json:"token"`
}

// TableName 返回asc_door 表名称
func (User) TableName() string {
	return gorm.DefaultTableNameHandler(nil, "user")
}

// AddUser insert a new User into database and returns
// last inserted Id on success.
func AddUser(m *User) (err error) {
	err = DB.Create(m).Error
	return err
}

// GetUserById retrieves User by Id. Returns error if
// Id doesn't exist
func GetUserById(id int) (user *User, err error) {
	user = new(User)
	if err = DB.First(&user, 10).Error; err == nil {
		user.Password = ""
		return user, nil
	}
	return nil, err
}

// GetUserByUsername retrieves User by username. Returns error if
// Id doesn't exist
func GetUserByUsername(username string) (user *User, err error) {
	user = new(User)
	if err = DB.Where("username = ?", username).Find(&user).Error; err == nil {
		user.Password = ""
		return user, nil
	}
	return nil, err
}

// GetAllUser retrieves all User matches certain condition. Returns empty list if
// no records exist
func GetAllUser() (users []*User, err error) {
	if err = DB.Order("id desc").Select("id,username,nickname").Find(&users).Error; err == nil {
		return users, nil
	}
	return nil, err
}

// UpdateUser updates User by Id and returns error if
// the record to be updated doesn't exist
func UpdateUserById(user *User) (err error) {
	// ascertain id exists in the database
	if err = DB.First(&user, user.Id).Error; err == nil {
		err = DB.Save(user).Error
	}
	return err
}

// DeleteUser deletes User by Id and returns error if
// the record to be deleted doesn't exist
func DeleteUser(id int) (err error) {
	user := User{}
	// ascertain id exists in the database
	if err = DB.First(&user, id).Error; err == nil {
		err = DB.Where("id = ?", id).Delete(user).Error
	}
	return err
}

func IsUserExist(username string) (exist bool, err error) {
	var count int
	if err = DB.Model(&User{}).Where("username = ?", username).Count(&count).Error; err == nil {
		return count > 0, nil
	}
	return false, err
}
