/*
 * Copyright (C) 2024 LEIDOS.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package gov.usdot.cv.msg.builder.util;

public class BitStringHelper {
    // This helper function returns the bit string after checking the attribute array
    public static int getBitString(int bitString, int bitStringLength, int[] attributesArray) {
        if (attributesArray.length > 0) {
            for (int bitIndex = 0; bitIndex < bitStringLength; bitIndex++) {
                if (checkIfIndexPresent(attributesArray, bitIndex)) {
                    bitString = setBit(bitString, (bitStringLength - bitIndex - 1), true);
                }
            }
        }
        return bitString;
    }

    // This function is used to loop through the given array and map it to an index
    public static boolean checkIfIndexPresent(int[] arr, int toCheckValue) {
        boolean valuePresent = false;
        for (int arrIndex = 0; arrIndex < arr.length; arrIndex++) {
            // Check if value in the array is equal to toCheckValue
            if (toCheckValue == arr[arrIndex]) {
                valuePresent = true;
                break;
            } else {
                valuePresent = false;
            }
        }
        return valuePresent;
    }

    // This function is used to set or clear a bit at a specified position
    public static int setBit(int num, int position, boolean set) {
        if (set) {
            // Set the bit to 1 at given position
            return num | (1 << position);
        } else {
            // Clear the bit to 0 at given position
            return num & ~(1 << position);
        }
    }
}
