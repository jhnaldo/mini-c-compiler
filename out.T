    AREA  SP
    AREA  FP
    AREA  VR
    AREA  MEM
LAB START
    MOVE  END MEM(0)
    MOVE  0 MEM(1)
    MOVE  1 SP
    MOVE  1 FP
    ADD   SP@ 1 VR(1)
    ADD   SP@ 10 SP
    ADD   SP@ 1 SP
    MOVE  VR(1)@ MEM(SP@)
    ADD   SP@ 1 SP
    MOVE  0 MEM(SP@)
    MOVE  SP@ FP
    JMP   main
LAB abc
    MOVE  1 VR(0)
LAB main
    ADD   SP@ 1 VR(1)
    ADD   SP@ 2 SP
    ADD   SP@ 1 SP
    MOVE  VR(1)@ MEM(SP@)
    MOVE  1 VR(0)
    MOVE  VR(0)@ VR(1)
    MOVE  0 VR(0)
    MOVE  VR(0)@ VR(2)
    ADD   FP@ 3 VR(0)
    ADD   MEM(VR(0)@)@ VR(2)@ VR(0)
    MOVE  VR(1)@ MEM(VR(0)@)
    MOVE  1 VR(0)
    MOVE  VR(0)@ VR(1)
    MOVE  0 VR(0)
    SUB   VR(1)@ VR(0)@ VR(0)
    MOVE  1 VR(1)
    JMPZ  VR(0)@ _L1
    MOVE  0 VR(1)
LAB _L1
    MOVE  VR(1)@ VR(0)
    JMPZ  VR(0)@ _L2
    MOVE  1 VR(0)
    WRITE VR(0)@
    JMP   _L3
LAB _L2
    MOVE  2 VR(0)
    WRITE VR(0)@
LAB _L3
    MOVE  0 VR(0)
    ADD   SP@ -3 SP
LAB END
