/*
 * Copyright 2016 Agapsys Tecnologia Ltda-ME.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.agapsys.security;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Leandro Oliveira (leandro@agapsys.com)
 */
public class SecurityTest {

	@BeforeClass
	public static void beforeClass() {
		MockedSecurity.allowMultipleInitialization();
		MockedSecurity.init(new MockedSecurityManager(), "com.agapsys.security.ProtectedClass");
	}
	
	private final MockedSecurityManager securityManager = (MockedSecurityManager) Security.getSecurityManager();
	
	// private ProtectedClass pc; // <-- Uncommenting this will cause javassist.CannotCompileException due to duplicate class definition
	
	@Before
	public void before() {
		securityManager.clearRoles();
	}
	
	@After
	public void after() {
		System.out.println();
	}
	
	@Test
	public void unprotectedTest() {
		securityManager.setAvailableRoles("CLASS_ROLE");

		ProtectedClass protectedClass = new ProtectedClass();
		protectedClass.unprotected();
		protectedClass.unprotectedWithAnnotation();
		ProtectedClass.staticUnprotected();
		ProtectedClass.staticUnprotectedWithAnnotation();
	}
	
	@Test
	public void staticMethods() {
		NotAllowedException error;
		
		// staticProtectedWithArgs OK ------------------------------------
		error = null;
		
		try {
			securityManager.setAvailableRoles("CLASS_ROLE", "ROLE");
			ProtectedClass.staticProtectedWithArgs("test");
		} catch (NotAllowedException ex) {
			error = ex;
		}
		
		Assert.assertNull(error);
		// staticProtectedWithArgs REJECTED ------------------------------------
		error = null;
		securityManager.setAvailableRoles("CLASS_ROLE");

		try {
			ProtectedClass.staticProtectedWithArgs("hello");
		} catch (NotAllowedException ex) {
			error = ex;
		}
		
		Assert.assertNotNull(error);
		// staticProtected OK---------------------------------------------------
		error = null;
		
		try {
			securityManager.setAvailableRoles("CLASS_ROLE", "ROLE");
			ProtectedClass.staticProtected();
		} catch (NotAllowedException ex) {
			error = ex;
		}
		
		Assert.assertNull(error);
		// staticProtected REJECTED --------------------------------------------
		error = null;
		securityManager.setAvailableRoles("CLASS_ROLE");

		try {
			ProtectedClass.staticProtected();
		} catch (NotAllowedException ex) {
			error = ex;
		}
		
		Assert.assertNotNull(error);
		// staticUnprotectedWithAnnotation OK ----------------------------------
		error = null;
		securityManager.setAvailableRoles("CLASS_ROLE");

		try {
			ProtectedClass.staticUnprotectedWithAnnotation();
			securityManager.setAvailableRoles("CLASS_ROLE", "test");
			ProtectedClass.staticUnprotectedWithAnnotation();
		} catch (NotAllowedException ex) {
			error = ex;
		}
		
		Assert.assertNull(error);
		// staticUnprotectedWithAnnotation REJECTED ----------------------------
		error = null;
		securityManager.clearRoles();

		try {
			ProtectedClass.staticUnprotectedWithAnnotation();
		} catch (NotAllowedException ex) {
			error = ex;
		}
		
		Assert.assertNotNull(error);
		// staticUnprotected OK ------------------------------------------------
		error = null;
		securityManager.setAvailableRoles("CLASS_ROLE");
		
		try {
			ProtectedClass.staticUnprotected();
			securityManager.setAvailableRoles("CLASS_ROLE", "test");
			ProtectedClass.staticUnprotected();
		} catch (NotAllowedException ex) {
			error = ex;
		}
		
		Assert.assertNull(error);
		// staticUnprotected REJECTED ------------------------------------------
		error = null;
		securityManager.clearRoles();
		
		try {
			ProtectedClass.staticUnprotected();
		} catch (NotAllowedException ex) {
			error = ex;
		}
		
		Assert.assertNotNull(error);
		// staticChainProtected OK ---------------------------------------------
		error = null;
		securityManager.clearRoles();

		try {
			securityManager.setAvailableRoles("CLASS_ROLE", "ROLE");
			ProtectedClass.staticChainProtected();
		} catch (NotAllowedException ex) {
			error = ex;
		}
		
		Assert.assertNull(error);
		// staticChainProtected REJECTED ---------------------------------------
		error = null;
		securityManager.clearRoles();

		try {
			ProtectedClass.staticChainProtected();
		} catch (NotAllowedException ex) {
			error = ex;
		}
		
		Assert.assertNotNull(error);
	}
	
	@Test
	public void instanceMethods() {
		NotAllowedException error;
		ProtectedClass protectedClass = new ProtectedClass();
		
		// staticProtectedWithArgs OK ------------------------------------
		error = null;
		
		try {
			securityManager.setAvailableRoles("CLASS_ROLE", "ROLE");
			protectedClass.protectedWithArgs("test");
		} catch (NotAllowedException ex) {
			error = ex;
		}
		
		Assert.assertNull(error);
		// staticProtectedWithArgs REJECTED ------------------------------------
		error = null;
		securityManager.setAvailableRoles("CLASS_ROLE");

		try {
			protectedClass.protectedWithArgs("hello");
		} catch (NotAllowedException ex) {
			error = ex;
		}
		
		Assert.assertNotNull(error);
		// staticProtected OK---------------------------------------------------
		error = null;
		
		try {
			securityManager.setAvailableRoles("CLASS_ROLE", "ROLE");
			protectedClass.protectedMethod();
		} catch (NotAllowedException ex) {
			error = ex;
		}
		
		Assert.assertNull(error);
		// staticProtected REJECTED --------------------------------------------
		error = null;
		securityManager.setAvailableRoles("CLASS_ROLE");

		try {
			protectedClass.protectedMethod();
		} catch (NotAllowedException ex) {
			error = ex;
		}
		
		Assert.assertNotNull(error);
		// staticUnprotectedWithAnnotation OK ----------------------------------
		error = null;
		securityManager.setAvailableRoles("CLASS_ROLE");

		try {
			protectedClass.unprotectedWithAnnotation();
		} catch (NotAllowedException ex) {
			error = ex;
		}
		
		Assert.assertNull(error);
		
		
		
		
		// staticUnprotectedWithAnnotation OK ----------------------------------
		error = null;
		securityManager.setAvailableRoles("CLASS_ROLE");

		try {
			protectedClass.unprotectedWithAnnotation();
		} catch (NotAllowedException ex) {
			error = ex;
		}
		
		Assert.assertNull(error);
		// staticUnprotected REJECTED ------------------------------------------
		error = null;
		securityManager.clearRoles();

		try {
			protectedClass.unprotected();
		} catch (NotAllowedException ex) {
			error = ex;
		}
		
		Assert.assertNotNull(error);
		// staticChainProtected OK ---------------------------------------------
		error = null;

		try {
			securityManager.setAvailableRoles("CLASS_ROLE", "ROLE");
			protectedClass.chainProtected();
		} catch (NotAllowedException ex) {
			error = ex;
		}
		
		Assert.assertNull(error);
		// staticChainProtected REJECTED ---------------------------------------
		error = null;
		securityManager.clearRoles();

		try {
			protectedClass.chainProtected();
		} catch (NotAllowedException ex) {
			error = ex;
		}
		
		Assert.assertNotNull(error);
	}
}