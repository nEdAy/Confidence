package util

import (
	"encoding/base64"
	"golang.org/x/crypto/scrypt"
)

func GetScryptPasswordBase64(password string) string {
	dkPassword, _ := scrypt.Key([]byte(password), []byte("nEdAy"), 32768, 8, 1, 32)
	passwordBase64 := base64.StdEncoding.EncodeToString(dkPassword)
	return passwordBase64
}
