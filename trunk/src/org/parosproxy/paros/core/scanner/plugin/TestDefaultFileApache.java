/*
 *
 * Paros and its related class files.
 * 
 * Paros is an HTTP/HTTPS proxy for assessing web application security.
 * Copyright (C) 2003-2004 Chinotec Technologies Company
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the Clarified Artistic License
 * as published by the Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Clarified Artistic License for more details.
 * 
 * You should have received a copy of the Clarified Artistic License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.parosproxy.paros.core.scanner.plugin;

import org.parosproxy.paros.core.scanner.AbstractDefaultFilePlugin;
import org.parosproxy.paros.core.scanner.Category;

public class TestDefaultFileApache extends AbstractDefaultFilePlugin {

	public int getId() {
		return 20000;
	}

	public String getName() {
		return "Apache default files";
	}

	public String[] getDependency() {
		return null;
	}

	public String getDescription() {
		return "Apache 1.3, 2.0 or 2.2 default files are found.";
	}

	public int getCategory() {
		return Category.SERVER;
	}

	public String getSolution() {
		return "Remove default files and virtual directories.";
	}

	public String getReference() {
		return "";
	}

	public void init() {
		super.init();
		createURI();
	}

	private void createURI() {

		addTest(
				"/icons",
				"a.gif,burst.png,generic.red.png,pie0.png,sound1.gif,a.png,c.gif,generic.sec.gif,pie1.gif,sound1.png,alert.black.gif,c.png,generic.sec.png  pie1.png,sound2.gif,alert.black.png,comp.blue.gif,hand.right.gif,pie2.gif,sound2.png,alert.red.gif,comp.blue.png,hand.right.png,pie2.png,sphere1.gif,alert.red.png,comp.gray.gif,hand.up.gif,pie3.gif,sphere1.png,apache_pb.gif,comp.gray.png,hand.up.png,pie3.png,sphere2.gif,apache_pb.png,compressed.gif,icon.sheet.gif,pie4.gif,sphere2.png,apache_pb2.gif,compressed.png,icon.sheet.png,pie4.png,tar.gif,apache_pb2.png,continued.gif,image1.gif,pie5.gif,tar.png,apache_pb2_ani.gif,continued.png,image1.png,pie5.png,tex.gif,back.gif,dir.gif,image2.gif,pie6.gif,tex.png,back.png,dir.png,image2.png,pie6.png,text.gif,ball.gray.gif,diskimg.gif,image3.gif,pie7.gif,text.png,ball.gray.png,diskimg.png,image3.png,pie7.png,transfer.gif,ball.red.gif,down.gif,index.gif,pie8.gif,transfer.png,ball.red.png,down.png,index.png,pie8.png,unknown.gif,binary.gif,dvi.gif,layout.gif,portal.gif,unknown.png,binary.png,dvi.png,layout.png,portal.png,up.gif,binhex.gif,f.gif,left.gif,ps.gif,up.png,binhex.png,f.png,left.png,ps.png,uu.gif,blank.gif,folder.gif,link.gif,quill.gif,uu.png,blank.png,folder.open.gif,link.png,quill.png,uuencoded.gif,bomb.gif,folder.open.png,movie.gif,right.gif,uuencoded.png,bomb.png,folder.png,movie.png,right.png,world1.gif,box1.gif,folder.sec.gif,p.gif,screw1.gif,world1.png,box1.png,folder.sec.png,p.png,screw1.png,world2.gif,box2.gif,forward.gif,patch.gif,screw2.gif,world2.png,box2.png,forward.png,patch.png,screw2.png,broken.gif,generic.gif,pdf.gif,script.gif,broken.png,generic.png,pdf.png,script.png,burst.gif,generic.red.gif,pie0.gif");
		addTest(
				"/manual",
				"bind.html,env.html,license.html,sitemap.html,caching.html,filter.html,logs.html,stopping.html,configuring.html,glossary.html,mpm.html,suexec.html,content-negotiation.html,handler.html,new_features_2_0.html,upgrading.html,custom-error.html,index.html,new_features_2_2.html,urlmapping.html,dns-caveats.html,install.html,sections.html,dso.html,invoking.html,server-wide.html");
		addTest("/cgi-bin", "test-cgi,printenv");
		addTest("/", "ServerStatus,ServerInfo");
		addTest(
				"/",
				"README.rus,apache_pb.gif,index.html.ca,index.html.cz,index.html.de,index.html.dk,index.html.ee,index.html.el,index.html.en,index.html.es,index.html.fr,index.html.he.iso8859-8,index.html.hu,index.html.it,index.html.ja.jis,index.html.kr.iso-kr,index.html.lb.utf8,index.html.nl,index.html.nn,index.html.no,index.html.po.iso-pl,index.html.pt,index.html.pt-br,index.html.ru.cp-1251,index.html.ru.cp866,index.html.ru.iso-ru,index.html.ru.koi8-r,index.html.ru.ucs2,index.html.ru.ucs4,index.html.ru.utf8,index.html.se,index.html.zh-tw.big5");

		// Apache 2.2 specific
		addTest(
				"/",
				"apache_pb22_ani.gif,apache_pb.gif,apache_pb.png,apache_pb22.gif,apache_pb22.png");
	}
}
