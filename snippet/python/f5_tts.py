import shutil
import sys
from datetime import datetime
import argparse
from gradio_client import Client, handle_file

# 命令行参数解析
parser = argparse.ArgumentParser(description="语音合成脚本")
parser.add_argument("--text", type=str, required=True, help="要合成的文本")
parser.add_argument("--ref", type=str, required=True, help="参考音频文件路径")
args = parser.parse_args()

text = args.text
ref_audio_path = args.ref

# 当前时间格式化为文件名
timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
output_filename = f"cloned_voice_{timestamp}.wav"

# 初始化客户端
client = Client("mrfakename/E2-F5-TTS")

# 发起预测请求
result = client.predict(
    handle_file(ref_audio_path), # 参考声音样本，用于克隆声音特征
    "",                          # 与参考音频对应的文字内容（可留空，主要用于语音识别对齐）
    text,                        # 要合成的文字（输出语音内容）
    False,                       # 是否去除参考音频中的静音段，设为 True 有助于提取更纯净的语音特征
    0.15,                        # 控制帧之间的过渡混合时间，过大可能会导致模糊和回音感，推荐在 0.08～0.15 之间
    32,                          # 推理步骤数（Number of Forward Evaluation steps），越大越清晰但速度越慢，推荐 32～64
    1.0,                         # 语音速度，1.0 表示正常速度，0.8 表示稍慢，1.2 表示稍快
    api_name="/basic_tts"
)

audio_path, spectrogram_url, transcript = result

# 保存音频文件
shutil.copy(audio_path, output_filename)

print(f"语音克隆完成，已保存为 {output_filename}")
print("实际参考转录：", transcript)