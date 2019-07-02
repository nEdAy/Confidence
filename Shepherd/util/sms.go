package util

import (
	"Shepherd/config"
	"github.com/pkg/errors"
	"github.com/tidwall/gjson"
	"gopkg.in/resty.v1"
)

var (
	InvalidJson       = errors.New("invalid json")
	AppkeyIsNull      = errors.New("AppKey为空")
	AppkeyIsIllegal   = errors.New("AppKey无效")
	PhoneOrCodeIsNull = errors.New("国家代码或手机号码为空")
	PhoneIsIllegal    = errors.New("手机号码格式错误")
	CodeIsNull        = errors.New("请求校验的验证码为空")
	IsFrequentChecks  = errors.New("请求校验验证码频繁（5分钟内同一个号码最多只能校验三次）")
	CodeIsError       = errors.New("验证码错误")
	ConfigIsError     = errors.New("没有打开服务端验证开关")
)

func VerifySMS(phone string, code string) (bool, error) {
	parameterMap := map[string]string{
		"appkey": config.Mob.AppKey,
		"phone":  phone,
		"zone":   "86",
		"code":   code,
	}
	response, err := resty.R().
		SetBody(parameterMap).
		Post("https://webapi.sms.mob.com/sms/verify")
	if err != nil {
		return false, err
	} else {
		if !gjson.Valid(response.String()) {
			return false, InvalidJson
		}
		status := gjson.Get(response.String(), "status").Int()
		switch status {
		case 200:
			return true, nil
		case 405:
			return false, AppkeyIsNull
		case 406:
			return false, AppkeyIsIllegal
		case 457:
			return false, PhoneOrCodeIsNull
		case 456:
			return false, PhoneIsIllegal
		case 466:
			return false, CodeIsNull
		case 467:
			return false, IsFrequentChecks
		case 468:
			return false, CodeIsError
		case 474:
			return false, ConfigIsError
		}
	}
}
