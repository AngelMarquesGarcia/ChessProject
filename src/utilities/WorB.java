/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package utilities;

/**
 *
 * @author Ángel Marqués García
 */
public enum WorB {
    WHITE,BLACK;

    public static WorB opposite(WorB c) {
        if (c == WHITE) 
            return BLACK;
        return WHITE;
    }
}
