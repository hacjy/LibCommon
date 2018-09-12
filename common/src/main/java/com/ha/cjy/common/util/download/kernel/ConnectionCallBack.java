package com.ha.cjy.common.util.download.kernel;

/**
 * TODO linqiang form AppMarketUtil.CommonCallBack
 * 描述:
 * @author linqiang(866116)
 */
public interface ConnectionCallBack<E> {
	public void invoke(final E... arg);
}
