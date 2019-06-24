package util

import (
	"crypto/md5"
	"encoding/hex"
	"github.com/dgrijalva/jwt-go"
	"github.com/nEdAy/wx_attendance_api_server/config"
	"time"
)

var jwtSecret = []byte(config.App.JwtSecret)

type Claims struct {
	Openid     string `json:"openid"`
	SessionKey string `json:"sessionKey"`
	jwt.StandardClaims
}

func GenerateToken(openid string, sessionKey string) (string, error) {
	nowTime := time.Now()
	expireTime := nowTime.Add(3 * time.Hour)

	claims := Claims{
		GetMd5String(openid),
		GetMd5String(sessionKey),
		jwt.StandardClaims{
			ExpiresAt: expireTime.Unix(),
			Issuer:    "gin-blog",
		},
	}

	tokenClaims := jwt.NewWithClaims(jwt.SigningMethodHS256, claims)
	token, err := tokenClaims.SignedString(jwtSecret)

	return token, err
}

func ParseToken(token string) (*Claims, error) {
	tokenClaims, err := jwt.ParseWithClaims(token, &Claims{}, func(token *jwt.Token) (interface{}, error) {
		return jwtSecret, nil
	})

	if tokenClaims != nil {
		if claims, ok := tokenClaims.Claims.(*Claims); ok && tokenClaims.Valid {
			return claims, nil
		}
	}

	return nil, err
}

func GetMd5String(s string) string {
	h := md5.New()
	h.Write([]byte(s))
	return hex.EncodeToString(h.Sum(nil))
}
