package com.ruoyi.gateway.modbus.simulation.enums;

import com.ruoyi.simulation.enums.RequestEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * SimulationMethod注解的返回值类型，返回该方法对应的名字
 */
public enum ModbusMethodEnum implements RequestEnum {
    Read01("线圈-读写位模式-read01",0), // 读线圈(读写位模式)
    Read02("读离散量输入-位只读模式-read02",1), // 读离散量输入(位只读模式)
    Read03("读保持寄存器-字节读写模式-read03",2), // 读保持寄存器(字节读写模式)
    Read04("读输入寄存器-字节只读模式-read04", 3), // 读输入寄存器(字节只读模式)

    Write05("写单个线圈(读写位模式)-write05", 4), // 写单个线圈(读写位模式)
    Write06(" 写单个保持寄存器-write06", 5), // 写单个保持寄存器
    Write0F("写多个线圈-write0F", 6), // 写多个线圈
    Write10("写多个保持寄存器-write10",7); // 写多个保持寄存器
    private final String name;
    private final int code;

    ModbusMethodEnum(String name, int code) {
        this.name = name;
        this.code = code;
    }


    @Override
    public String getMethodName() {
        return name;
    }

    @Override
    public int getCode() {
        return code;
    }


    public static ModbusMethodEnum INSTANCE(int code) {
        switch (code) {
            case 0: return Read01;
            case 1: return Read02;
            case 2: return Read03;
            case 3: return Read04;

            case 4: return Write05;
            case 5: return Write06;
            case 6: return Write0F;
            case 7: return Write10;

            default: throw new IllegalStateException("不存在功能码["+code+"]");
        }
    }

}
