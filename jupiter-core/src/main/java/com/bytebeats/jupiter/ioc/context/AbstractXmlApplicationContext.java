package com.bytebeats.jupiter.ioc.context;

import com.bytebeats.jupiter.ioc.beans.factory.AbstractBeanFactory;

public abstract class AbstractXmlApplicationContext implements ApplicationContext {

	protected AbstractBeanFactory beanFactory;

	public AbstractXmlApplicationContext(AbstractBeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public void refresh() throws Exception {
		loadBeanDefinitions(beanFactory);
		onRefresh();
	}

	protected abstract void loadBeanDefinitions(AbstractBeanFactory beanFactory) throws Exception;

	protected void onRefresh() throws Exception{
		beanFactory.preInstantiateSingletons();
	}
}
