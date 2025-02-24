package com.nectux.mizan.hyban.utils;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRAbstractBeanDataSourceProvider;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


public class GenericDataSource<E> extends JRAbstractBeanDataSourceProvider {

	private List<E> listEntity;
	
	public GenericDataSource(Class<E> entityClass) {
		super(entityClass);
	}
	
	@Override
	public JRDataSource create(JasperReport jrReport) throws JRException {

		listEntity = new ArrayList<E>();

		return new JRBeanCollectionDataSource(listEntity);
	}

	@Override
	public void dispose(JRDataSource jrds) throws JRException {
		listEntity.clear();
		listEntity= null;
	}
	
	public JRDataSource create(JasperReport jrReport, List<E> listEntity) throws JRException {
		return new JRBeanCollectionDataSource(listEntity);
		
	}

}
