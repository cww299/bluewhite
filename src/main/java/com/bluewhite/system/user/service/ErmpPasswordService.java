package com.bluewhite.system.user.service;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.credential.HashingPasswordService;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.bluewhite.common.utils.security.Md5Utils;
import com.bluewhite.system.user.entity.User;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;



public class ErmpPasswordService implements HashingPasswordService {

    @Autowired
    private CacheManager ehcacheManager;

    private Cache loginRecordCache;

	//   @Value(value = "${user.password.maxRetryCount}")
    private int maxRetryCount = 10;

    public void setMaxRetryCount(int maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
    }

    @PostConstruct
    public void init() {
        loginRecordCache = ehcacheManager.getCache("loginRecordCache");
    }

    public void validate(User user, String password) {
        String username = user.getUserName();
        int retryCount = 0;
        if (!matches(user, password)) {
        } else {
        }
    }

    public boolean matches(User user, String newPassword) {
        return user.getPassword().equals(encryptPassword(user.getUserName(), newPassword));
    }

    public void clearLoginRecordCache(String username) {
        loginRecordCache.remove(username);
    }

    
    public String encryptPassword(String username, String password) {
        return Md5Utils.hash(username + password);
    }

	@Override
	public String encryptPassword(Object plaintextPassword)
			throws IllegalArgumentException {
		return null;
	}

	@Override
	public boolean passwordsMatch(Object submittedPlaintext, String encrypted) {
		return true;
	}

	@Override
	public Hash hashPassword(Object plaintext) throws IllegalArgumentException {
		RandomNumberGenerator rng = new SecureRandomNumberGenerator();
		ByteSource bs = rng.nextBytes();
		String salt = bs.toString();
		
		Hash hash = new Md5Hash(plaintext, salt);
		return hash;
	}

	@Override
	public boolean passwordsMatch(Object plaintext, Hash savedPasswordHash) {
		
		return true;
	}

}
