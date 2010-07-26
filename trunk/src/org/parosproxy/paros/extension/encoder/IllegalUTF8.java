/*
 * Copyright (C) 2010, Andiparos Project, Axel Neumann
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see http://www.gnu.org/copyleft/
 * 
 */

package org.parosproxy.paros.extension.encoder;

import java.util.Vector;

public class IllegalUTF8 {
	
	public static String encode(String input, int bytes) {
		char [] input_array = input.toCharArray();
		Vector<String> output_array = new Vector<String>();
		
		for(char c : input_array) {
			
			String char_in_hex = "";
			
			if (bytes == 4) {
				char_in_hex = "%" + Integer.toHexString(0xff & ((byte) 0xf0))
							+ "%" + Integer.toHexString(0xff & ((byte) 0x80))
							+ "%" + Integer.toHexString(0xff & ((byte) (0x80 | ((c & 0x7f)>>6))))
							+ "%" + Integer.toHexString(0xff & ((byte) (0x80 | (c & 0x3f))));
			} else if (bytes == 3) {
				
				char_in_hex = "%" + Integer.toHexString(0xff & ((byte) 0xe0))
							+ "%" + Integer.toHexString(0xff & ((byte) (0x80 | ((c & 0x7f)>>6))))
							+ "%" + Integer.toHexString(0xff & ((byte) (0x80 | (c & 0x3f))));
			} else {
				char_in_hex = "%" + Integer.toHexString(0xff & ((byte) (0xc0 | ((c & 0x7f)>>6))))
							+ "%" + Integer.toHexString(0xff & ((byte) (0x80 | (c & 0x3f))));
			}
			
			output_array.add(char_in_hex);
		}
		
		String output = "";
        for(int i = 0; i < output_array.size(); i++)
        {
            output += output_array.get(i);
        }
		
		return output;
	}
	
}
