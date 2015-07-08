# Microcontroller Simulator [![Build Status](https://travis-ci.org/tferraro/microcontroller.svg)](https://travis-ci.org/tferraro/microcontroller)

Microcontroller Simulator made in Java8.

### Microcontroller settings:

**Main Memory:** 10 bit memory addresses (total of 1024 bytes).

**Registers:** 2 1 byte Registers (A and B), being B the Register the one of the less significative byte.

**Instruction Set:** BATIS.

**Operation Set:** Load | Start | Execute | Step | StepBack | Stop.

### BATIS(Basic Assembler Type Instruction Set) Instruction Set:

| Name | Parameters |                         Description                           |
| ---- | ---------- | ------------------------------------------------------------- |
| NOP  |            | No Operation                                                  |
| ADD  |            | Adds Registers Values                                         |
| SUB  |            | Substracts Register A value from Register B value             |
| DIV  |            | Divide Register B Value with Register A value                 |
| SWAP |            | Swaps Registers Values                                        |
| LOD  |  **addr**  | Loads Register A with the memory address **addr** value       |
| STR  |  **addr**  | Loads Register A Value on the memory address **addr**         |
| LODV |  **val**   | Loads Register A with **val**                                 |
| IFNZ |  **goTo**  | If Register A is zero, jumps to **goTo** instruction          | 
| WHNZ | **goBack** | While Register A is not zero, jumps to **goBack** instruction |

