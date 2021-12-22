package com.tjy.feign.fallback;

import com.tjy.feign.AccountFeign;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Description
 */
@Component
public class AccountFeignFallbackFactory implements FallbackFactory<AccountFeign> {
	@Override
	public AccountFeign create(Throwable throwable) {
		AccountFeignFallback accountFeignFallback = new AccountFeignFallback();
		accountFeignFallback.setCause(throwable);
		return accountFeignFallback;
	}
}
