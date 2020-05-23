## Project 01 - 영화 상세 화면

<details><summary> 1. layout_gravity와 gravity 차이 </summary>
<div>
    layout_gravity는 자기의 부모 위젯을 기준으로 하여 자신의 위치를 잡는다.

    gravity는 자기 자식의 위치를 이동하라는 의미이다.

    자기 자식들의 위치를 정하고 싶을 땐 gravity를 부모 위젯 기준으로 적음.
</div>

</details>
<details><summary> 2. 메모장 파일 읽어오기 </summary>
<div>
    보통 res 폴더를 만들어 여기에 txt 파일을 넣는다.
    이후, inputstream에 getresources().openrawresources(id)로 가져온다.

    ```
    if (in != null) {
        InputStreamReader stream = new InputStreamReader(in, "utf-8");
        BufferedReader buffer = new BufferedReader(stream);

        String read;
        StringBuilder sb = new StringBuilder("");

        while ((read = buffer.readLine()) != null) {
            sb.append(read);
            sb.append('\n');
        }
        in.close();
        // id : textView01 TextView를 불러와서
        //메모장에서 읽어온 문자열을 등록한다.
        TextView textView = (TextView) findViewById(R.id.story);
        textView.setText(sb.toString());
    }
    ```
</div>
</details>