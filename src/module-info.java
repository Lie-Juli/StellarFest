module StellarFest {
	requires java.sql;
	requires javafx.graphics;
	requires javafx.base;
	requires javafx.controls;
	
	opens main;
	opens view;
	opens model;
	opens controller;
	opens util;
}