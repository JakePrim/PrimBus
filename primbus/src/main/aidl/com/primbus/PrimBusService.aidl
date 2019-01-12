// PrimBusService.aidl
package com.primbus;

// Declare any non-default types here with import statements
import com.primbus.Request;
import com.primbus.Responce;

interface PrimBusService{
Responce send(in Request request);
}
